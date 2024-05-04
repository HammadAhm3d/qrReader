package com.qrreader

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


class QRScannerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

    private var mCallback: Callback? = null

    override fun getName(): String {
        return "QRScannerModule"
    }
    @ReactMethod
    fun openCustomScanner(isBeepEnable: Boolean, isOrientationLocked: Boolean, prompt: String?, barcodeTypes: ReadableArray?, callback: Callback) {
        mCallback = callback
        val activity = currentActivity
        val types = getBarcodesTypes(barcodeTypes)
        if (activity != null) {
            IntentIntegrator(activity)
                .setPrompt(prompt ?: "")
                .setBeepEnabled(isBeepEnable)
                .setDesiredBarcodeFormats(types)
                .setOrientationLocked(isOrientationLocked)
                .setCaptureActivity(CustomScannerActivity::class.java)
                .initiateScan()
            reactApplicationContext.addActivityEventListener(this)
        }
    }


    private fun getBarcodesTypes(barcodeTypes: ReadableArray?): ArrayList<String>? {
        if (barcodeTypes == null) {
            return null
        }
        val objects = barcodeTypes.toArrayList()
        val types = ArrayList<String>()
        for (type in objects) {
            types.add(type as String)
        }
        return types
    }
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = when (result.formatName) {
            "EAN_13" -> multiFormatWriter.encode(result.contents, BarcodeFormat.EAN_13, 500, 250)
            "EAN_8" -> multiFormatWriter.encode(result.contents, BarcodeFormat.EAN_8, 500, 250)
            "UPC_A" -> multiFormatWriter.encode(result.contents, BarcodeFormat.UPC_A, 500, 250)
            "UPC_E" -> multiFormatWriter.encode(result.contents, BarcodeFormat.UPC_E, 500, 250)
            "QR_CODE" -> multiFormatWriter.encode(result.contents, BarcodeFormat.QR_CODE, 500, 500)
            "DATA_MATRIX" -> multiFormatWriter.encode(result.contents, BarcodeFormat.DATA_MATRIX, 500, 500)
            "AZTEC" -> multiFormatWriter.encode(result.contents, BarcodeFormat.AZTEC, 500, 500)
            else -> throw IllegalArgumentException("Unsupported format: ${result.formatName}")
        }
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
        val base64Image = bitmapToBase64(bitmap)
        mCallback?.invoke(result.contents, base64Image)
        // Remove the listener since we are removing this activity.
        reactApplicationContext.removeActivityEventListener(this)
    }

    override fun onNewIntent(intent: Intent) {}
}

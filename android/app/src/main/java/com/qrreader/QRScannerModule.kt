package com.qrreader

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


class QRScannerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

    private var mCallback: Callback? = null

    override fun getName(): String {
        return "QRScannerModule"
    }

    @ReactMethod
    fun openScanner(isBeepEnable: Boolean, prompt: String?, callback: Callback) {
        mCallback = callback
        val activity = currentActivity
        if (activity != null) {
            IntentIntegrator(activity)
                .setPrompt(prompt ?: "")
                .setBeepEnabled(isBeepEnable)
                .initiateScan()
            reactApplicationContext.addActivityEventListener(this)
        }
    }

    @ReactMethod
    fun openCustomScanner(isBeepEnable: Boolean, isOrientationLocked: Boolean, barcodeTypes: ReadableArray?, callback: Callback) {
        mCallback = callback
        val activity = currentActivity
        val types = getBarcodesTypes(barcodeTypes)
        if (activity != null) {
            IntentIntegrator(activity)
                .setBeepEnabled(isBeepEnable)
                .setDesiredBarcodeFormats(types)
                .setOrientationLocked(isOrientationLocked)
                .setCaptureActivity(QRScannerActivity::class.java)
                .initiateScan()
            reactApplicationContext.addActivityEventListener(this)
        }
    }

    @ReactMethod
    fun openScannerWithPhoto(isBeepEnable: Boolean, prompt: String?, barcodeTypes: ReadableArray?, callback: Callback) {
        mCallback = callback
        val activity = currentActivity
        val types = getBarcodesTypes(barcodeTypes)
        if (activity != null) {
            IntentIntegrator(activity)
                .setPrompt(prompt ?: "")
                .setBeepEnabled(isBeepEnable)
                .setDesiredBarcodeFormats(types)
                .setBarcodeImageEnabled(true)
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

    override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        mCallback?.invoke(result.contents, result.barcodeImagePath)
        // Remove the listener since we are removing this activity.
        reactApplicationContext.removeActivityEventListener(this)
    }

    override fun onNewIntent(intent: Intent) {}
}

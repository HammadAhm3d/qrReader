//package com.qrreader
//
//import android.content.Intent
//import com.facebook.react.bridge.ReactApplicationContext
//import com.facebook.react.bridge.ReactContextBaseJavaModule
//import com.facebook.react.bridge.ReactMethod
//import com.facebook.react.modules.core.DeviceEventManagerModule
//import com.facebook.react.bridge.WritableMap
//import com.facebook.react.bridge.Arguments
//import com.facebook.react.bridge.Callback
//
//class QRScannerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
//
//    // Declare reactContext as a class-level property
//    private val reactContext: ReactApplicationContext = reactContext
//
//    override fun getName(): String {
//        return "QRScannerModule"
//    }
//
//    @ReactMethod
//    fun startScan() {
//        val currentActivity = currentActivity
//        if (currentActivity != null) {
//            val intent = Intent(currentActivity, QRScannerActivity::class.java)
//            currentActivity.startActivity(intent)
//        }
//    }
//    @ReactMethod
//    fun sendScannedData(data: String) {
//        val params = mapOf("scannedData" to data)
////         Use the class-level reactContext property
//        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
//            .emit("onQRScanned", params)
//
//    }
//}

package id.andgen.andgen.actions.barcode_scanner.codes.src.main

object AndroidManifestXml {

    const val FILE_NAME = "AndroidManifest"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android">

                <uses-permission android:name="android.permission.CAMERA" />
                <uses-permission android:name="android.permission.VIBRATE" />
            
                <application android:hardwareAccelerated="true">
                    <activity
                        android:name=".BarcodeScannerActivity"
                        android:exported="false"
                        android:theme="@style/Theme.BarcodeScanner" />
                </application>
            
            </manifest>
        """.trimIndent()
    }
}
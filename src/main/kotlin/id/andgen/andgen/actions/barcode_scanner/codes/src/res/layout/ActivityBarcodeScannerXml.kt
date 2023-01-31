package id.andgen.andgen.actions.barcode_scanner.codes.src.res.layout

object ActivityBarcodeScannerXml {

    const val FILE_NAME = "activity_barcode_scanner"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                android:id="@+id/container"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fitsSystemWindows="true"
                tools:context=".BarcodeScannerActivity" />
        """.trimIndent()
    }
}
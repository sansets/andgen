package id.andgen.andgen.actions.barcode_scanner.codes.src.res.drawable

object BaselineArrowBack24Xml {

    const val FILE_NAME = "baseline_arrow_back_24"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <vector xmlns:android="http://schemas.android.com/apk/res/android"
                android:width="24dp"
                android:height="24dp"
                android:autoMirrored="true"
                android:tint="#000000"
                android:viewportWidth="24"
                android:viewportHeight="24">
                <path
                    android:fillColor="@android:color/white"
                    android:pathData="M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z" />
            </vector>
        """.trimIndent()
    }
}
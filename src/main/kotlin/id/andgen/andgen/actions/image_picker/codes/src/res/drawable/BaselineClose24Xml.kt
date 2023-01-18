package id.andgen.andgen.actions.image_picker.codes.src.res.drawable

object BaselineClose24Xml {

    const val FILE_NAME = "baseline_close_24"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <vector xmlns:android="http://schemas.android.com/apk/res/android"
                android:width="24dp"
                android:height="24dp"
                android:tint="#000000"
                android:viewportWidth="24"
                android:viewportHeight="24">
                <path
                    android:fillColor="@android:color/white"
                    android:pathData="M19,6.41L17.59,5 12,10.59 6.41,5 5,6.41 10.59,12 5,17.59 6.41,19 12,13.41 17.59,19 19,17.59 13.41,12z" />
            </vector>
        """.trimIndent()
    }
}
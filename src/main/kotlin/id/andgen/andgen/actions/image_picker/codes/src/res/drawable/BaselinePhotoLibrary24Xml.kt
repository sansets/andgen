package id.andgen.andgen.actions.image_picker.codes.src.res.drawable

object BaselinePhotoLibrary24Xml {

    const val FILE_NAME = "baseline_photo_library_24"
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
                    android:pathData="M22,16L22,4c0,-1.1 -0.9,-2 -2,-2L8,2c-1.1,0 -2,0.9 -2,2v12c0,1.1 0.9,2 2,2h12c1.1,0 2,-0.9 2,-2zM11,12l2.03,2.71L16,11l4,5L8,16l3,-4zM2,6v14c0,1.1 0.9,2 2,2h14v-2L4,20L4,6L2,6z" />
            </vector>
        """.trimIndent()
    }
}
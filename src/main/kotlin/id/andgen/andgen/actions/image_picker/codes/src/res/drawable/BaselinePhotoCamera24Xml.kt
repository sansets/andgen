package id.andgen.andgen.actions.image_picker.codes.src.res.drawable

object BaselinePhotoCamera24Xml {

    const val FILE_NAME = "baseline_photo_camera_24"
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
                    android:pathData="M12,12m-3.2,0a3.2,3.2 0,1 1,6.4 0a3.2,3.2 0,1 1,-6.4 0" />
                <path
                    android:fillColor="@android:color/white"
                    android:pathData="M9,2L7.17,4L4,4c-1.1,0 -2,0.9 -2,2v12c0,1.1 0.9,2 2,2h16c1.1,0 2,-0.9 2,-2L22,6c0,-1.1 -0.9,-2 -2,-2h-3.17L15,2L9,2zM12,17c-2.76,0 -5,-2.24 -5,-5s2.24,-5 5,-5 5,2.24 5,5 -2.24,5 -5,5z" />
            </vector>
        """.trimIndent()
    }
}
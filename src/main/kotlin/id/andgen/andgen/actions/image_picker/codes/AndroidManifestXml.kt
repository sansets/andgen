package id.andgen.andgen.actions.image_picker.codes

object AndroidManifestXml {

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android">

            </manifest>
        """.trimIndent()
    }
}
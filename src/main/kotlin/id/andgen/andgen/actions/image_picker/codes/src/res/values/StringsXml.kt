package id.andgen.andgen.actions.image_picker.codes.src.res.values

object StringsXml {

    const val FILE_NAME = "strings"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <string name="title_select_image">Select Image</string>
                <string name="title_camera">Camera</string>
                <string name="title_gallery">Gallery</string>
            </resources>
        """.trimIndent()
    }
}
package id.andgen.andgen.actions.image_picker.codes.src.res.values_in

object StringsInXml {

    const val FILE_NAME = "strings"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <string name="title_select_image">Pilih Gambar</string>
                <string name="title_camera">Kamera</string>
                <string name="title_gallery">Galeri</string>
            </resources>
        """.trimIndent()
    }
}
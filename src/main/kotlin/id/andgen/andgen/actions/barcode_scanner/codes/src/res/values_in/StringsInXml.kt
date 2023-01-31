package id.andgen.andgen.actions.barcode_scanner.codes.src.res.values_in

object StringsInXml {

    const val FILE_NAME = "strings"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <string name="text_camera_access_required">Akses kamera diperlukan</string>

                <string name="hint_scanner">Sejajarkan Kode QR untuk memindainya dengan kamera perangkat Anda.</string>
            </resources>
        """.trimIndent()
    }
}
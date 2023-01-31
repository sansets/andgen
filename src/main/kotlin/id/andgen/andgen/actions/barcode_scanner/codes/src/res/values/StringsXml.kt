package id.andgen.andgen.actions.barcode_scanner.codes.src.res.values

object StringsXml {

    const val FILE_NAME = "strings"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <string name="text_camera_access_required">Camera access is required</string>

                <string name="hint_scanner">Line up QR Code to scan it with your device’s camera.</string>
            </resources>
        """.trimIndent()
    }
}
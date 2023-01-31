package id.andgen.andgen.actions.barcode_scanner.codes.src.res.values

object ThemesXml {

    const val FILE_NAME = "themes"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <style name="Theme.BarcodeScanner" parent="Theme.MaterialComponents.Light.NoActionBar" />
            </resources>
        """.trimIndent()
    }
}
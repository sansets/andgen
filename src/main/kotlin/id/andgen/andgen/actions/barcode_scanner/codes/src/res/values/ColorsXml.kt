package id.andgen.andgen.actions.barcode_scanner.codes.src.res.values

object ColorsXml {

    const val FILE_NAME = "colors"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <color name="colorBlack25">#40000000</color>
            </resources>
        """.trimIndent()
    }
}
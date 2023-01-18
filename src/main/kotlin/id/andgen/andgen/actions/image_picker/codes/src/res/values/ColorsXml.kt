package id.andgen.andgen.actions.image_picker.codes.src.res.values

object ColorsXml {

    const val FILE_NAME = "colors"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <color name="colorBackground">#FCFCFC</color>
                <color name="colorTextPrimary">#212121</color>
                <color name="colorStroke">#E0E0E0</color>
                <color name="colorIcon">#616161</color>
            </resources>
        """.trimIndent()
    }
}
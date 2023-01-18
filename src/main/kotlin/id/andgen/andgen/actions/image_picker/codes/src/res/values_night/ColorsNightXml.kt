package id.andgen.andgen.actions.image_picker.codes.src.res.values_night

object ColorsNightXml {

    const val FILE_NAME = "colors"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <resources>
                <color name="colorBackground">#181D24</color>
                <color name="colorTextPrimary">#FAFAFA</color>
                <color name="colorStroke">#757575</color>
                <color name="colorIcon">#f1f1f1</color>
            </resources>
        """.trimIndent()
    }
}
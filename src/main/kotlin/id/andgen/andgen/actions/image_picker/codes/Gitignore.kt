package id.andgen.andgen.actions.image_picker.codes

object Gitignore {

    const val FILE_NAME = ""
    const val EXTENSION = "gitignore"

    fun getCode(): String {
        return """
            /build
        """.trimIndent()
    }
}
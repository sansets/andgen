package id.andgen.andgen.actions.barcode_scanner.codes

object Gitignore {

    const val FILE_NAME = ""
    const val EXTENSION = "gitignore"

    fun getCode(): String {
        return """
            /build
        """.trimIndent()
    }
}
package id.andgen.andgen.util.ext

fun String?.toPackageName(): String {
    if (!this.orEmpty().contains("src")) {
        return ""
    }

    return this
        ?.replaceBefore("src", "")
        ?.split("/")
        ?.drop(3)
        ?.filter { it.isNotEmpty() }
        ?.joinToString(".")
        .orEmpty()
}
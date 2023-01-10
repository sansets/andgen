package id.andgen.andgen.util.ext

import id.andgen.jsontokotlin.JsonToKotlinBuilder
import id.andgen.jsontokotlin.model.TargetJsonConverter


fun String?.toKotlinString(
    packageName: String?,
    className: String?,
): String {
    return JsonToKotlinBuilder()
        .setPackageName(packageName.orEmpty())
        .setAnnotationLib(TargetJsonConverter.NoneWithCamelCase)
        .build(
            input = this.orEmpty(),
            className = className.orEmpty(),
        )
}

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
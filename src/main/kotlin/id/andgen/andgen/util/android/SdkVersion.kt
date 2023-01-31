package id.andgen.andgen.util.android

data class SdkVersion(
    val name: String,
    val version: Int,
) {
    override fun toString(): String {
        return name
    }
}

fun getSdkVersions(): List<SdkVersion> {
    return listOf(
        SdkVersion(
            name = "API 16: Android 4.1 (Jelly Bean)",
            version = 16,
        ),
        SdkVersion(
            name = "API 17: Android 4.2 (Jelly Bean)",
            version = 17,
        ),
        SdkVersion(
            name = "API 18: Android 4.3 (Jelly Bean)",
            version = 18,
        ),
        SdkVersion(
            name = "API 19: Android 4.4 (Kitkat)",
            version = 19,
        ),
        SdkVersion(
            name = "API 20: Android 4.4W (Kitkat Wear)",
            version = 20,
        ),
        SdkVersion(
            name = "API 21: Android 5.0 (Lollipop)",
            version = 21,
        ),
        SdkVersion(
            name = "API 22: Android 5.1 (Lollipop)",
            version = 22,
        ),
        SdkVersion(
            name = "API 23: Android 6.0 (Marshmallow)",
            version = 23,
        ),
        SdkVersion(
            name = "API 24: Android 7.0 (Nougat)",
            version = 24,
        ),
        SdkVersion(
            name = "API 25: Android 7.1.1 (Nougat)",
            version = 25,
        ),
        SdkVersion(
            name = "API 26: Android 8.0 (Oreo)",
            version = 26,
        ),
        SdkVersion(
            name = "API 27: Android 8.1 (Oreo)",
            version = 27,
        ),
        SdkVersion(
            name = "API 28: Android 9.0 (Pie)",
            version = 28,
        ),
        SdkVersion(
            name = "API 29: Android 10.0 (Q)",
            version = 29,
        ),
        SdkVersion(
            name = "API 30: Android 11.0 (R)",
            version = 30,
        ),
        SdkVersion(
            name = "API 31: Android 12.0 (S)",
            version = 31,
        ),
        SdkVersion(
            name = "API 32: Android 12L (Sv2)",
            version = 32,
        ),
        SdkVersion(
            name = "API 33: Android Tiramisu",
            version = 33,
        ),
    )
}

package id.andgen.andgen.util.ext

import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.StandardCharsets

fun InputStream?.convertToString(): String {
    return IOUtils.toString(this, StandardCharsets.UTF_8)
}
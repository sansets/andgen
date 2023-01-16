package id.andgen.andgen.util.ext

import com.intellij.openapi.application.Application

fun Application.writeFile(runnable: Runnable) {
    if (isDispatchThread) {
        runWriteAction(runnable)
    } else {
        invokeLater {
            runWriteAction(runnable)
        }
    }
}
package id.andgen.jsontokotlin.model.classscodestruct

import id.andgen.jsontokotlin.utils.numberOf

data class Annotation(val annotationTemplate: String, val rawName: String) {

    fun getAnnotationString(): String {
        return if (annotationTemplate.contains("%s")) {
            val countS = annotationTemplate.numberOf("%s")
            val args = Array(countS) { rawName }
            annotationTemplate.format(*args)
        } else {
            annotationTemplate
        }
    }

    companion object {

        private fun fromOneLineAnnotationString(annotationString: String): Annotation {
            if (annotationString.contains("\n")) {
                throw IllegalArgumentException("Only support one line annotation!! current is $annotationString")
            }

            return if (annotationString.contains("\"")) {
                if (annotationString.numberOf("\"") != 2) {
                    throw IllegalArgumentException("Only support one line annotation with one couple Double quotes!! current is $annotationString")
                }
                val rawName = annotationString.substringAfter("\"").substringBefore("\"")
                val annotationTemplate =
                    annotationString.substringBefore("\"") + "\"%s\"" + annotationString.substringAfterLast("\"")
                Annotation(annotationTemplate, rawName)
            } else {
                Annotation(annotationString, "")
            }

        }

        private fun fromMultipleLineAnnotationString(multipleLineString: String): Annotation {
            val annotations = multipleLineString.trim().split("\n").map { fromOneLineAnnotationString(it) }
            val annotationTemplate = annotations.joinToString(separator = "\n") { it.annotationTemplate }
            val rawName = annotations.firstOrNull { it.rawName.isNotBlank() }?.rawName ?: ""
            return Annotation(annotationTemplate, rawName)
        }

        fun fromAnnotationString(annotationString: String): Annotation {
            return if (annotationString.trim().contains("\n").not()) {
                fromOneLineAnnotationString(annotationString)
            } else {
                fromMultipleLineAnnotationString(annotationString)
            }
        }
    }
}

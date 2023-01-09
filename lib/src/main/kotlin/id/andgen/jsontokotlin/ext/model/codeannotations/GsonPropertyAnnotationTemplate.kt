package id.andgen.jsontokotlin.ext.model.codeannotations

import id.andgen.jsontokotlin.ext.model.classscodestruct.Annotation

class GsonPropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    companion object {

        const val propertyAnnotationFormat = "@SerializedName(\"%s\")"
    }

    private val annotation = Annotation(propertyAnnotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }
}
package id.andgen.jsontokotlin.model.codeannotations

class LoganSquarePropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    companion object {

        const val propertyAnnotationFormat = "@JsonField(name = arrayOf(\"%s\"))"
    }

    private val annotation = id.andgen.jsontokotlin.model.classscodestruct.Annotation(propertyAnnotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<id.andgen.jsontokotlin.model.classscodestruct.Annotation> {
        return listOf(annotation)
    }
}

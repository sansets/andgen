package id.andgen.jsontokotlin.model.codeannotations

class SerializablePropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    companion object{

        const val annotationFormat = "@SerialName(\"%s\")"
    }

    private val annotation = id.andgen.jsontokotlin.model.classscodestruct.Annotation(annotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<id.andgen.jsontokotlin.model.classscodestruct.Annotation> {
        return listOf(annotation)
    }
}
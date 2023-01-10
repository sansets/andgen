package id.andgen.jsontokotlin.model.codeannotations


interface AnnotationTemplate {
    fun getCode(): String
    fun getAnnotations(): List<id.andgen.jsontokotlin.model.classscodestruct.Annotation>
}
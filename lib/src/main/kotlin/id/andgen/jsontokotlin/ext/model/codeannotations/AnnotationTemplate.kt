package id.andgen.jsontokotlin.ext.model.codeannotations


interface AnnotationTemplate {
    fun getCode(): String
    fun getAnnotations(): List<Annotation>
}
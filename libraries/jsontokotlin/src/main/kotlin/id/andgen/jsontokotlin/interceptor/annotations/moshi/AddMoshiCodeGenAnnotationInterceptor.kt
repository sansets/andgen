package id.andgen.jsontokotlin.interceptor.annotations.moshi

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.model.classscodestruct.DataClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import id.andgen.jsontokotlin.model.codeelements.KPropertyName

/**
 * This interceptor try to add Moshi(code gen) annotation
 */
class AddMoshiCodeGenAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makePropertyName(it.originName, true)
                it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            val classAnnotationString = "@JsonClass(generateAdapter = true)"

            val classAnnotation = id.andgen.jsontokotlin.model.classscodestruct.Annotation.fromAnnotationString(classAnnotationString)

            kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties, annotations = listOf(classAnnotation))
        } else {
            kotlinClass
        }
    }
}
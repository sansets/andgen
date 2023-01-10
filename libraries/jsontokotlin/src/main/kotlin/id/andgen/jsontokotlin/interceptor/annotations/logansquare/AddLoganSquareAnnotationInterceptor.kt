package id.andgen.jsontokotlin.interceptor.annotations.logansquare

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.model.classscodestruct.DataClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.codeannotations.LoganSquarePropertyAnnotationTemplate
import id.andgen.jsontokotlin.model.codeelements.KPropertyName

class AddLoganSquareAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            val addLoganSquareAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations =  LoganSquarePropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
            }

            val classAnnotationString = "@JsonObject"

            val classAnnotation = id.andgen.jsontokotlin.model.classscodestruct.Annotation.fromAnnotationString(classAnnotationString)


            return kotlinClass.copy(properties = addLoganSquareAnnotationProperties,annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }
}

package id.andgen.jsontokotlin.interceptor.annotations.jackson

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.model.classscodestruct.DataClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.codeannotations.JacksonPropertyAnnotationTemplate
import id.andgen.jsontokotlin.model.codeelements.KPropertyName


class AddJacksonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations =  JacksonPropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
            }

            kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties)
        } else {
            kotlinClass
        }
    }
}

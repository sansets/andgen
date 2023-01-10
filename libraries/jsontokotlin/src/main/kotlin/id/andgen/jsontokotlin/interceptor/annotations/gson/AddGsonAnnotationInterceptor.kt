package id.andgen.jsontokotlin.interceptor.annotations.gson

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.model.classscodestruct.DataClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.codeannotations.GsonPropertyAnnotationTemplate
import id.andgen.jsontokotlin.model.codeelements.KPropertyName

class AddGsonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            val addGsonAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations = GsonPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            kotlinClass.copy(properties = addGsonAnnotationProperties)
        } else {
            kotlinClass
        }

    }

}

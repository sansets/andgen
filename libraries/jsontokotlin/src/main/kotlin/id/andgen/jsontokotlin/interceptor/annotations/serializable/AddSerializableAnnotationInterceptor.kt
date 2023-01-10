package id.andgen.jsontokotlin.interceptor.annotations.serializable

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.model.classscodestruct.DataClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.codeannotations.SerializablePropertyAnnotationTemplate
import id.andgen.jsontokotlin.model.codeelements.KPropertyName

class AddSerializableAnnotationInterceptor: IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            val addCustomAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                val annotations = SerializablePropertyAnnotationTemplate(it.originName).getAnnotations()

                it.copy(annotations = annotations,name = camelCaseName)
            }

            val classAnnotationString = "@Serializable"

            val classAnnotation = id.andgen.jsontokotlin.model.classscodestruct.Annotation.fromAnnotationString(classAnnotationString)

            return kotlinClass.copy(properties = addCustomAnnotationProperties,annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }

}
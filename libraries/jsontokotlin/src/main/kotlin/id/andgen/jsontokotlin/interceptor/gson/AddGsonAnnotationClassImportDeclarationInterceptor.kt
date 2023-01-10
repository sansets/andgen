package id.andgen.jsontokotlin.interceptor.gson

import id.andgen.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddGsonAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    companion object{

        const val propertyAnnotationImportClassString ="import com.google.gson.annotations.SerializedName"
    }

    override fun intercept(originClassImportDeclaration: String): String {

        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}

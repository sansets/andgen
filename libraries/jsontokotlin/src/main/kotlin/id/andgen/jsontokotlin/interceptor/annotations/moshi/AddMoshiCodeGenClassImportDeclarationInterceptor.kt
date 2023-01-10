package id.andgen.jsontokotlin.interceptor.annotations.moshi

import id.andgen.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

/**
 * try to add import class declarations of Moshi Generation
 */
class AddMoshiCodeGenClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {

        val propertyAnnotationImportClassString = "import com.squareup.moshi.Json"

        val classAnnotationImportClassString = "import com.squareup.moshi.JsonClass"

        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
                .append(classAnnotationImportClassString)
    }
}

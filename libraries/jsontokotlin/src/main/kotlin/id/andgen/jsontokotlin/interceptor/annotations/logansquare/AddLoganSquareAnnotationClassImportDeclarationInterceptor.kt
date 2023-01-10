package id.andgen.jsontokotlin.interceptor.annotations.logansquare

import id.andgen.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddLoganSquareAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {

        val propertyAnnotationImportClassString = "import com.bluelinelabs.logansquare.annotation.JsonField"

        val classAnnotationImportClassString = "import com.bluelinelabs.logansquare.annotation.JsonObject"

        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
                .append(classAnnotationImportClassString)
    }

}

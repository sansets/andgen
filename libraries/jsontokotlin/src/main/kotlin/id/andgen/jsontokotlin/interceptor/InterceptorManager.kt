package id.andgen.jsontokotlin.interceptor

import id.andgen.jsontokotlin.interceptor.annotations.fastjson.AddFastJsonAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.fastjson.AddFastjsonAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.jackson.AddJacksonAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.jackson.AddJacksonAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.logansquare.AddLoganSquareAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.logansquare.AddLoganSquareAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.moshi.AddMoshiAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.moshi.AddMoshiAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.moshi.AddMoshiCodeGenAnnotationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.moshi.AddMoshiCodeGenClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.serializable.AddSerializableAnnotationClassImportDeclarationInterceptor
import id.andgen.jsontokotlin.interceptor.annotations.serializable.AddSerializableAnnotationInterceptor
import id.andgen.jsontokotlin.model.ConfigManager
import id.andgen.jsontokotlin.model.TargetJsonConverter
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass

object InterceptorManager {

    fun getEnabledKotlinDataClassInterceptors(): List<IKotlinClassInterceptor<KotlinClass>> {

        return mutableListOf<IKotlinClassInterceptor<KotlinClass>>().apply {

            if (ConfigManager.isPropertiesVar) {
                add(ChangePropertyKeywordToVarInterceptor())
            }

            add(PropertyTypeNullableStrategyInterceptor())

            when (ConfigManager.targetJsonConverterLib) {
                TargetJsonConverter.None -> {
                }
                TargetJsonConverter.NoneWithCamelCase -> add(MakePropertiesNameToBeCamelCaseInterceptor())
                TargetJsonConverter.Gson -> add(AddGsonAnnotationInterceptor())
                TargetJsonConverter.FastJson -> add(AddFastJsonAnnotationInterceptor())
                TargetJsonConverter.Jackson -> add(AddJacksonAnnotationInterceptor())
                TargetJsonConverter.MoShi -> add(AddMoshiAnnotationInterceptor())
                TargetJsonConverter.MoshiCodeGen -> add(AddMoshiCodeGenAnnotationInterceptor())
                TargetJsonConverter.LoganSquare -> add(AddLoganSquareAnnotationInterceptor())
                TargetJsonConverter.Serializable -> add(AddSerializableAnnotationInterceptor())
            }

            if (ConfigManager.isCommentOff) {
                add(CommentOffInterceptor)
            }

            if (ConfigManager.isOrderByAlphabetical) {
                add(OrderPropertyByAlphabeticalInterceptor())
            }

        }.apply {
            if (ConfigManager.enableMinimalAnnotation) {
                add(MinimalAnnotationKotlinClassInterceptor())
            }
            add(FinalKotlinClassWrapperInterceptor())
        }
    }


    fun getEnabledImportClassDeclarationInterceptors(): List<IImportClassDeclarationInterceptor> {
        return mutableListOf<IImportClassDeclarationInterceptor>().apply {
            when (ConfigManager.targetJsonConverterLib) {
                TargetJsonConverter.Gson->add(AddGsonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.FastJson-> add(AddFastjsonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.Jackson-> add(AddJacksonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.MoShi->add(AddMoshiAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.MoshiCodeGen->add(AddMoshiCodeGenClassImportDeclarationInterceptor())
                TargetJsonConverter.LoganSquare->add(AddLoganSquareAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.Serializable->add(AddSerializableAnnotationClassImportDeclarationInterceptor())
                else->{}
            }
        }
    }

}

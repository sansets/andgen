package id.andgen.jsontokotlin.utils

import id.andgen.jsontokotlin.interceptor.IKotlinClassInterceptor
import id.andgen.jsontokotlin.interceptor.InterceptorManager
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass

class KotlinClassCodeMaker(private val kotlinClass: KotlinClass, private val generatedFromJSONSchema: Boolean = false) {

    fun makeKotlinClassCode(): String {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return makeKotlinClassCode(interceptors)
    }

    private fun makeKotlinClassCode(interceptors: List<IKotlinClassInterceptor<KotlinClass>>): String {
        var kotlinClassForCodeGenerate = kotlinClass
        kotlinClassForCodeGenerate = kotlinClassForCodeGenerate.applyInterceptors(interceptors)

        val resolveNameConflicts = kotlinClassForCodeGenerate.resolveNameConflicts()
        val allModifiableClassesRecursivelyIncludeSelf = resolveNameConflicts
            .getAllModifiableClassesRecursivelyIncludeSelf()
        return if (generatedFromJSONSchema) { //don't remove class when their properties are same
            allModifiableClassesRecursivelyIncludeSelf
                .joinToString("\n\n") { it.getOnlyCurrentCode() }
        } else {
            allModifiableClassesRecursivelyIncludeSelf.distinctByPropertiesAndSimilarClassName()
                .joinToString("\n\n") { it.getOnlyCurrentCode() }
        }
    }
}

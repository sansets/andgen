package id.andgen.jsontokotlin.model.classscodestruct

import id.andgen.jsontokotlin.model.builder.ICodeBuilder

/**
 * Created by Seal.Wu on 2019-11-24
 * Kotlin class which could not be modified the code content also with generic type
 */
abstract class UnModifiableGenericClass : GenericKotlinClass {
    override val modifiable: Boolean = false
    override fun getCode(): String = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override fun getOnlyCurrentCode(): String = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override fun rename(newName: String): KotlinClass = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override val codeBuilder: ICodeBuilder<*> = ICodeBuilder.EMPTY
}
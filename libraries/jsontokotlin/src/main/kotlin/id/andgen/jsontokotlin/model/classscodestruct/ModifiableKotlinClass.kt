package id.andgen.jsontokotlin.model.classscodestruct

interface ModifiableKotlinClass : KotlinClass {

    override val modifiable: Boolean
        get() = true


}
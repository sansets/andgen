package id.andgen.jsontokotlin.model.builder

import id.andgen.jsontokotlin.model.classscodestruct.ListClass

/**
 * kotlin list code generator
 *
 * Created by Nstd on 2020/6/30 15:59.
 */
class KotlinListClassCodeBuilder : ICodeBuilder<ListClass> {

    override fun getCode(clazz: ListClass): String {
        clazz.run {
            return if (generic.modifiable.not()) {
                getOnlyCurrentCode()
            } else {
                """
            class $name : ArrayList<${generic.name}>(){
${referencedClasses.filter { it.modifiable }.joinToString("\n\n") { it.getCode().prependIndent("            $indent") }}
            }
        """.trimIndent()
            }
        }
    }

    override fun getOnlyCurrentCode(clazz: ListClass): String {
        clazz.run {
            return """
            class $name : ArrayList<${generic.name}>()
        """.trimIndent()
        }
    }
    companion object{
        val DEFAULT = KotlinListClassCodeBuilder()
    }
}
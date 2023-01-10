package id.andgen.jsontokotlin.utils.classgenerator

import com.google.gson.Gson
import com.google.gson.JsonArray
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.model.classscodestruct.ListClass
import id.andgen.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-20
 * Generate List Class from JsonArray String
 */
class ListClassGeneratorByJSONArray(private val className: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)
    private val jsonArrayExcludeNull = jsonArray.filterOutNullElement()

    fun generate(): ListClass {

        when {
            jsonArray.size() == 0 -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArray.allItemAreNullElement() -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArrayExcludeNull.allElementAreSamePrimitiveType() -> {

                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArrayExcludeNull[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArrayExcludeNull) else p.toKotlinClass()
                return ListClass(name = className, generic = elementKotlinClass)
            }
            jsonArrayExcludeNull.allItemAreObjectElement() -> {
                val fatJsonObject = jsonArrayExcludeNull.getFatJsonObject()
                val itemObjClassName = "${className}Item"
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                return ListClass(className, dataClassFromJsonObj)
            }
            jsonArrayExcludeNull.allItemAreArrayElement() -> {
                val fatJsonArray = jsonArrayExcludeNull.getFatJsonArray()
                val itemArrayClassName = "${className}SubList"
                val listClassFromFatJsonArray = ListClassGeneratorByJSONArray(itemArrayClassName, fatJsonArray.toString()).generate()
                return ListClass(className, listClassFromFatJsonArray)
            }
            else -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
        }
    }
}

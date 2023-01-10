package id.andgen.jsontokotlin.utils.classgenerator

import com.google.gson.Gson
import com.google.gson.JsonArray
import id.andgen.jsontokotlin.model.classscodestruct.GenericListClass
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-23
 * Generate `List<$ItemType>` from json array string and json array's json key
 */
class GenericListClassGeneratorByJSONArray(private val jsonKey: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)
    private val jsonArrayExcludeNull = jsonArray.filterOutNullElement()


    fun generate(): GenericListClass {

        when {
            jsonArray.size() == 0 -> {
                return GenericListClass(generic = KotlinClass.ANY)
            }
            jsonArray.allItemAreNullElement() -> {
                return GenericListClass(generic = KotlinClass.ANY)
            }
            jsonArrayExcludeNull.allElementAreSamePrimitiveType() -> {
                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArrayExcludeNull[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArrayExcludeNull) else p.toKotlinClass()
                return GenericListClass(generic = elementKotlinClass)
            }
            jsonArrayExcludeNull.allItemAreObjectElement() -> {
                val fatJsonObject = jsonArrayExcludeNull.getFatJsonObject()
                val itemObjClassName = getRecommendItemName(jsonKey)
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                return GenericListClass(generic = dataClassFromJsonObj)
            }
            jsonArrayExcludeNull.allItemAreArrayElement() -> {
                val fatJsonArray = jsonArrayExcludeNull.getFatJsonArray()
                val genericListClassFromFatJsonArray = GenericListClassGeneratorByJSONArray(jsonKey, fatJsonArray.toString()).generate()
                return GenericListClass(generic = genericListClassFromFatJsonArray)
            }
            else -> {
                return GenericListClass(generic = KotlinClass.ANY)
            }
        }
    }

    private fun getRecommendItemName(jsonKey: String): String {
        return adjustPropertyNameForGettingArrayChildType(jsonKey)
    }
}

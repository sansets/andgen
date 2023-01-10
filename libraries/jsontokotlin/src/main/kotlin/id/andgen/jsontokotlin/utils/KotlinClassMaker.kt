package id.andgen.jsontokotlin.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import id.andgen.jsontokotlin.model.classscodestruct.KotlinClass
import id.andgen.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONObject
import id.andgen.jsontokotlin.utils.classgenerator.ListClassGeneratorByJSONArray

class KotlinClassMaker(private val rootClassName: String, private val json: String) {

    fun makeKotlinClass(): KotlinClass {
        return when {
            json.isJSONObject() -> DataClassGeneratorByJSONObject(rootClassName, Gson().fromJson(json, JsonObject::class.java)).generate()
            json.isJSONArray() -> ListClassGeneratorByJSONArray(rootClassName, json).generate()
            else -> throw IllegalStateException("Can't generate Kotlin Data Class from a no JSON Object/JSON Object Array")
        }
    }

    private fun String.isJSONObject(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return jsonElement.isJsonObject
    }

    private fun String.isJSONArray(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return jsonElement.isJsonArray
    }
}



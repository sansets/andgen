package id.andgen.jsontokotlin

import com.winterbe.expekt.should
import id.andgen.jsontokotlin.model.PropertyTypeStrategy
import id.andgen.jsontokotlin.model.TargetJsonConverter
import org.junit.Test
import id.andgen.jsontokotlin.model.DefaultValueStrategy

class JsonToKotlinBuilderTest {

    @Test
    fun build() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertiesVar() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                var name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableVarProperties(true)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyAutoDeterMineNullableOrNot() {


        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any?
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyNullable() {


        val input = """
            {"name": "john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String?
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setPropertyTypeStrategy(PropertyTypeStrategy.Nullable)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyNotNullable() {


        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setPropertyTypeStrategy(PropertyTypeStrategy.NotNullable)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setDefaultValueStrategyNone() {


        val input = """
            {"name": "john", "company": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String,
                val company: Any?
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setDefaultValueStrategy(DefaultValueStrategy.None)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibNoneWithCamelCase() {

        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val userName: String,
                val companyName: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setAnnotationLib(TargetJsonConverter.NoneWithCamelCase)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibNone() {

        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val user_name: String,
                val company_name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setAnnotationLib(TargetJsonConverter.None)
            .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCommentEnabled() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableComments(true)
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCommentDisabled() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableComments(false)
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setOrderByAlphabeticEnabled() {
        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company_name: String,
                val user_name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableOrderByAlphabetic(true)
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setOrderByAlphabeticDisabled() {
        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val user_name: String,
                val company_name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableOrderByAlphabetic(false)
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setInnerClassModelDisabled() {
        val input = """
            {
                "glossary":{
                    "title":"example glossary",
                    "GlossDiv":{
                        "title":"S",
                        "GlossList":{
                            "GlossEntry":{
                                "ID":"SGML",
                                "SortAs":"SGML",
                                "GlossTerm":"Standard Generalized Markup Language",
                                "Acronym":"SGML",
                                "Abbrev":"ISO 8879:1986",
                                "GlossDef":{
                                    "para":"A meta-markup language, used to create markup languages such as DocBook.",
                                    "GlossSeeAlso":[
                                        "GML",
                                        "XML"
                                    ]
                                },
                                "GlossSee":"markup"
                            }
                        }
                    }
                }
            }
        """.trimIndent()

        val expectedOutput = """
            data class GlossResponse(
                val glossary: Glossary
            )

            data class Glossary(
                val title: String,
                val GlossDiv: GlossDiv
            )

            data class GlossDiv(
                val title: String,
                val GlossList: GlossList
            )

            data class GlossList(
                val GlossEntry: GlossEntry
            )

            data class GlossEntry(
                val ID: String,
                val SortAs: String,
                val GlossTerm: String,
                val Acronym: String,
                val Abbrev: String,
                val GlossDef: GlossDef,
                val GlossSee: String
            )

            data class GlossDef(
                val para: String,
                val GlossSeeAlso: List<String>
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .enableOrderByAlphabetic(false)
            .enableInnerClassModel(false)
            .build(input, "GlossResponse")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPackageName() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            package com.my.package.name
            
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
            .setPackageName("com.my.package.name")
            .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }
}
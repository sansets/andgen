package id.andgen.jsontokotlin

import id.andgen.jsontokotlin.model.DefaultValueStrategy
import id.andgen.jsontokotlin.model.PropertyTypeStrategy
import id.andgen.jsontokotlin.model.TargetJsonConverter
import id.andgen.jsontokotlin.test.TestConfig
import id.andgen.jsontokotlin.utils.KotlinClassCodeMaker
import id.andgen.jsontokotlin.utils.KotlinClassMaker


/**
 * To get Kotlin class code from JSON
 */
class JsonToKotlinBuilder {

    private var packageName = ""

    init {
        TestConfig.apply {

            isTestModel = true
            isCommentOff = true
            isOrderByAlphabetical = false
            isPropertiesVar = false
            targetJsonConvertLib = TargetJsonConverter.None
            propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = false
            enableMinimalAnnotation = false
            indent = 4
            parenClassTemplate = ""
            isKeywordPropertyValid = true
            extensionsConfig = ""
        }
    }

    /**
     * To set property type to `var`, pass true.
     * Default : false
     */
    fun enableVarProperties(isVar: Boolean): JsonToKotlinBuilder {
        TestConfig.isPropertiesVar = isVar
        return this
    }

    /**
     * To set if the properties can be null or not
     * Default: PropertyTypeStrategy.NotNullable
     */
    fun setPropertyTypeStrategy(strategy: PropertyTypeStrategy): JsonToKotlinBuilder {
        TestConfig.propertyTypeStrategy = strategy
        return this
    }

    /**
     * To set default value.
     * Default : DefaultValueStrategy.AvoidNull
     */
    fun setDefaultValueStrategy(strategy: DefaultValueStrategy): JsonToKotlinBuilder {
        TestConfig.defaultValueStrategy = strategy
        return this
    }

    /**
     * To set JSON decoding/encoding library
     * Default: TargetJsonConverter.None
     */
    fun setAnnotationLib(library: TargetJsonConverter): JsonToKotlinBuilder {
        TestConfig.targetJsonConvertLib = library
        return this
    }

    /**
     * If enabled, value will be commented right to the property
     * Default: false
     */
    fun enableComments(isEnable: Boolean): JsonToKotlinBuilder {
        TestConfig.isCommentOff = !isEnable
        return this
    }


    /**
     * If enabled properties will be ordered in alphabetic order
     * Default : false
     */
    fun enableOrderByAlphabetic(isOrderByAlphabetic: Boolean): JsonToKotlinBuilder {
        TestConfig.isOrderByAlphabetical = isOrderByAlphabetic
        return this
    }


    /**
     * If enabled, classes will be nested with in it's parent class.
     * Default : false
     */
    fun enableInnerClassModel(isInnerClassModel: Boolean): JsonToKotlinBuilder {
        TestConfig.isNestedClassModel = isInnerClassModel
        return this
    }

    /**
     *
     */
    fun enableMapType(isMapType: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMapType = isMapType
        return this
    }

    fun enableCreateAnnotationOnlyWhenNeeded(isOnlyWhenNeeded: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMinimalAnnotation = isOnlyWhenNeeded
        return this
    }

    /**
     * To set indent in output kotlin code.
     *
     * Default : 4
     * @see <a href="https://github.com/theapache64/JsonToKotlinClass/blob/35fad98bd071feb3ce9493dd1c16866ed1dee7ca/library/src/test/kotlin/wu/seal/jsontokotlin/JsonToKotlinBuilderTest.kt#L799">test case</a>
     */
    fun setIndent(indent: Int): JsonToKotlinBuilder {
        TestConfig.indent = indent
        return this
    }

    fun setParentClassTemplate(parentClassTemplate: String): JsonToKotlinBuilder {
        TestConfig.parenClassTemplate = parentClassTemplate
        return this
    }

    fun build(
        input: String,
        className: String,
    ): String {
        val kotlinClass = KotlinClassMaker(
            className,
            input
        ).makeKotlinClass()

        val classCode = KotlinClassCodeMaker(
            kotlinClass
        ).makeKotlinClassCode()

        return if (packageName.isNotBlank()) {
            "package $packageName\n\n$classCode"
        } else {
            classCode
        }

    }

    fun setPackageName(packageName: String): JsonToKotlinBuilder {
        this.packageName = packageName
        return this
    }


}
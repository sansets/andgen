package id.andgen.jsontokotlin.model

import id.andgen.jsontokotlin.test.TestConfig
import id.andgen.jsontokotlin.test.TestConfig.isTestModel

/**
 * ConfigManager
 * main purpose to obtain the detail corresponding config And the entry of modify
 * Created by Seal.Wu on 2017/9/13.
 */

interface IConfigManager {

    private val IS_PROPERTIES_VAR_KEY: String
        get() = "isPropertiesVar_key"

    private val TARGET_JSON_CONVERTER_LIB_KEY: String
        get() = "target_json_converter_lib_key"

    private val IS_COMMENT_OFF: String
        get() = "need_comment_key"

    private val IS_APPEND_ORIGINAL_JSON: String
        get() = "is_append_original_json"

    private val IS_ORDER_BY_ALPHABETICAL: String
        get() = "is_order_by_alphabetical"

    private val PROPERTY_TYPE_STRATEGY_KEY: String
        get() = "jsontokotlin_is_property_property_type_strategy_key"

    private val INIT_WITH_DEFAULT_VALUE_KEY: String
        get() = "jsonToKotlin_init_with_default_value_key"

    private val DEFAULT_VALUE_STRATEGY_KEY: String
        get() = "jsonToKotlin_default_value_strategy_key"

    private val USER_UUID_KEY: String
        get() = "jsonToKotlin_user_uuid_value_key"


    private val USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS: String
        get() = "jsonToKotlin_user_custom_json_lib_annotation_import_class"

    private val USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING: String
        get() = "jsontokotlin_user_custom_json_lib_annotation_format_string"

    private val USER_CUSTOM_JSON_LIB_CLASS_ANNOTATION_FORMAT_STRING: String
        get() = "jsontokotlin_user_custom_json_lib_class_annotation_format_string"

    private val INNER_CLASS_MODEL_KEY: String
        get() = "jsontokotlin_inner_class_model_key"


    var isPropertiesVar: Boolean
        get() = TestConfig.isPropertiesVar
        set(value) {
            TestConfig.isPropertiesVar = value
        }

    var isAppendOriginalJson: Boolean
        get() = TestConfig.isAppendOriginalJson
        set(value) {
            TestConfig.isAppendOriginalJson = value
        }

    var isCommentOff: Boolean
        get() = TestConfig.isCommentOff
        set(value) {
            TestConfig.isCommentOff = value
        }

    var isOrderByAlphabetical: Boolean
        get() = TestConfig.isOrderByAlphabetical
        set(value) {
            TestConfig.isOrderByAlphabetical = value
        }

    var targetJsonConverterLib: TargetJsonConverter
        get() = if (isTestModel) TestConfig.targetJsonConvertLib else {
            TargetJsonConverter.None
        }
        set(value) {
            TestConfig.targetJsonConvertLib = value
        }

    var propertyTypeStrategy: PropertyTypeStrategy
        get() = TestConfig.propertyTypeStrategy
        set(value) {
            TestConfig.propertyTypeStrategy = value
        }

    var defaultValueStrategy: DefaultValueStrategy
        get() = TestConfig.defaultValueStrategy
        set(value) {
            TestConfig.defaultValueStrategy = value
        }

    var customAnnotationClassImportdeclarationString: String
        get() = TestConfig.customAnnotaionImportClassString
        set(value) {
            TestConfig.customAnnotaionImportClassString = value
        }

    var customPropertyAnnotationFormatString: String
        get() = TestConfig.customPropertyAnnotationFormatString
        set(value) {
            TestConfig.customPropertyAnnotationFormatString = value
        }

    var customClassAnnotationFormatString: String
        get() = TestConfig.customClassAnnotationFormatString
        set(value) {
            TestConfig.customClassAnnotationFormatString = value
        }

    var isInnerClassModel: Boolean
        get() = TestConfig.isNestedClassModel
        set(value) {
            TestConfig.isNestedClassModel = value
        }

}




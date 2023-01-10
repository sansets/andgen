package id.andgen.jsontokotlin.model

import id.andgen.jsontokotlin.test.TestConfig

/**
 * Config Manager
 * Created by Seal.Wu on 2018/2/7.
 */
object ConfigManager : IConfigManager {

    private const val INDENT_KEY = "json-to-kotlin-class-indent-space-number"
    private const val ENABLE_MAP_TYP_KEY = "json-to-kotlin-class-enable-map-type"
    private const val ENABLE_MINIMAL_ANNOTATION = "json-to-kotlin-class-enable-minimal-annotation"
    private const val PARENT_CLASS_TEMPLATE = "json-to-kotlin-class-parent-class-template"
    private const val KEYWORD_PROPERTY_EXTENSIONS_CONFIG = "json-to-kotlin-class-keyword-extensions-config"
    private const val AUTO_DETECT_JSON_SCHEMA = "json-to-kotlin-class-auto-detect-json-schema"

    var indent: Int
        get() = 4
        set(value) {
            TestConfig.indent = value
        }

    var enableMapType: Boolean
        get() = TestConfig.enableMapType
        set(value) {
            TestConfig.enableMapType = value
        }

    var enableMinimalAnnotation: Boolean
        get() = TestConfig.enableMinimalAnnotation
        set(value) {
            TestConfig.enableMinimalAnnotation = value
        }

    var autoDetectJsonScheme: Boolean
        get() = TestConfig.autoDetectJsonScheme
        set(value) {
            TestConfig.autoDetectJsonScheme = value
        }

    var parenClassTemplate: String
        get() = TestConfig.parenClassTemplate
        set(value) {
            TestConfig.parenClassTemplate = value
        }

    var extensionsConfig: String
        get() = TestConfig.extensionsConfig
        set(value) {
            TestConfig.extensionsConfig = value
        }

}
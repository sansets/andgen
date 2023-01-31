package id.andgen.andgen.actions.barcode_scanner.codes

object BuildGradle {

    const val FILE_NAME = "build"
    const val EXTENSION = "gradle"

    fun getCode(
        packageName: String,
        minimumSdkVersion: Int,
    ): String {
        return """
            plugins {
                id 'com.android.library'
                id 'org.jetbrains.kotlin.android'
            }
            
            android {
                namespace '$packageName'
                compileSdk 33
            
                defaultConfig {
                    minSdk $minimumSdkVersion
                    targetSdk 33
            
                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles "consumer-rules.pro"
                }
            
                buildTypes {
                    release {
                        minifyEnabled false
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    }
                }
                compileOptions {
                    sourceCompatibility JavaVersion.VERSION_1_8
                    targetCompatibility JavaVersion.VERSION_1_8
                }
                kotlinOptions {
                    jvmTarget = '1.8'
                }
            
                buildFeatures {
                    viewBinding true
                }
            }
            
            dependencies {
                implementation 'androidx.core:core-ktx:1.9.0'
                implementation 'androidx.appcompat:appcompat:1.6.0'
                implementation 'com.google.android.material:material:1.8.0'
            
                // QrCodeScanner
                implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
            
                // Permission
                implementation "com.karumi:dexter:6.2.2"
            
                testImplementation 'junit:junit:4.13.2'
                androidTestImplementation 'androidx.test.ext:junit:1.1.5'
                androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
            }
        """.trimIndent()
    }
}
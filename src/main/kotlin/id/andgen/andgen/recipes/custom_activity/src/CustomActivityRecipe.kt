package id.andgen.andgen.recipes.custom_activity.src

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import id.andgen.andgen.recipes.custom_activity.src.app_package.customActivityJava
import id.andgen.andgen.recipes.custom_activity.src.app_package.customActivityKt

fun RecipeExecutor.customActivityRecipe(
    moduleData: ModuleTemplateData,
    activityClass: String,
    addHilt: Boolean,
    layoutName: String,
    packageName: String,
) {
    val (projectData, srcOut, resOut, manifestOut) = moduleData
    val ktOrJavaExt = projectData.language.extension
    val appCompatVersion = moduleData.apis.appCompatVersion
    val useAndroidX = projectData.androidXSupport

    addAllKotlinDependencies(moduleData)

    mergeXml(
        source = androidManifestXml(activityClass, moduleData.isLibrary, false, packageName),
        to = manifestOut.resolve("AndroidManifest.xml")
    )

    generateSimpleLayout(moduleData, activityClass, layoutName)

    addDependency("com.android.support:appcompat-v7:$appCompatVersion.+")

    if (addHilt) {
        applyPlugin(plugin = "dagger.hilt.android.plugin", revision = null)
        applyPlugin(plugin = "kotlin-kapt", revision = null)
        addClasspathDependency("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
        addDependency("com.google.dagger:hilt-android:2.28-alpha")
        addDependency("com.google.dagger:hilt-android-compiler:2.28-alpha", "kapt")
    }

    val customActivity = when (projectData.language) {
        Language.Java -> customActivityJava(activityClass, true, layoutName, packageName, useAndroidX)
        Language.Kotlin -> customActivityKt(activityClass, true, layoutName, packageName, useAndroidX)
    }

    save(source = customActivity, to = srcOut.resolve("$activityClass.$ktOrJavaExt"))

    open(srcOut.resolve("$activityClass.$ktOrJavaExt"))
}
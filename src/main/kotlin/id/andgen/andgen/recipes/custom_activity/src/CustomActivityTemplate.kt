package id.andgen.andgen.recipes.custom_activity.src

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import java.io.File

object CustomActivityTemplate : Template {

    private val packageName = defaultPackageNameParameter
    private val addHilt = booleanParameter {
        name = "Add Hilt as a dependency"
        default = true
        help = " If true, adds Hilt dependencies to Gradle"
    }
    private val activityClass = stringParameter {
        name = "Activity Name"
        default = "MainActivity"
        help = "The name of the activity class to create"
        constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
        suggest = { layoutToActivity(layoutName.value) }
    }
    private val layoutName = stringParameter {
        name = "Layout Name"
        default = "activity_main"
        help = "The name of the UI layout to create for the activity"
        constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
    }

    override val category: Category
        get() = Category.Activity
    override val constraints: Collection<TemplateConstraint>
        get() = emptyList()
    override val description: String
        get() = "Creates a new custom activity for android"
    override val documentationUrl: String?
        get() = null
    override val formFactor: FormFactor
        get() = FormFactor.Mobile
    override val minSdk: Int
        get() = 23
    override val name: String
        get() = "Custom Activity"
    override val recipe: Recipe
        get() = {
            customActivityRecipe(
                it as ModuleTemplateData,
                activityClass.value,
                addHilt.value,
                layoutName.value,
                packageName.value,
            )
        }
    override val uiContexts: Collection<WizardUiContext>
        get() = listOf(
            WizardUiContext.MenuEntry,
            WizardUiContext.NewProject,
            WizardUiContext.NewProjectExtraDetail,
            WizardUiContext.NewModule,
            WizardUiContext.ActivityGallery,
        )
    override val widgets: Collection<Widget<*>>
        get() = listOf(
            TextFieldWidget(activityClass),
            CheckBoxWidget(addHilt),
            TextFieldWidget(layoutName),
            PackageNameWidget(packageName),
        )

    override fun thumb(): Thumb {
        return Thumb { findResource(this.javaClass, File("thumb/template_new_project.png")) }
    }
}
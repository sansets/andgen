package id.andgen.andgen.recipes.custom_activity

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import id.andgen.andgen.recipes.custom_activity.src.CustomActivityTemplate

class CustomWizardTemplateProvider : WizardTemplateProvider() {

    override fun getTemplates(): List<Template> {
        return listOf(CustomActivityTemplate)
    }
}
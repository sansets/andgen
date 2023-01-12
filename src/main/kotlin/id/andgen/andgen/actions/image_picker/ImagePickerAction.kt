package id.andgen.andgen.actions.image_picker

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

class ImagePickerAction : AnAction() {

    private val applicationManager = ApplicationManager.getApplication()
    private var previousOutputPath = ""

    override fun actionPerformed(event: AnActionEvent) {
        showGeneratorDialog(event.project)
    }

    private fun showGeneratorDialog(project: Project?) {
        val dialog = ImagePickerForm(
            project = project,
        )

        // Generate if button 'OK' clicked
        if (dialog.showAndGet()) {

        }
    }
}
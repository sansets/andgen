package id.andgen.andgen.actions.json_to_data_class

import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.file.PsiDirectoryFactory
import id.andgen.andgen.StringResources
import id.andgen.andgen.util.ext.convertToString
import id.andgen.andgen.util.ext.showNotification
import id.andgen.jsontokotlin.ext.toKotlinString
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class JsonToDataClassAction : AnAction() {

    private val applicationManager = ApplicationManager.getApplication()
    private var previousOutputPath = ""

    override fun actionPerformed(event: AnActionEvent) {
        showGeneratorDialog(event.project)
    }

    private fun showGeneratorDialog(project: Project?) {
        val dialog = JsonToDataClassForm(
            project = project,
            previousOutputPath = previousOutputPath,
        )

        // Generate if button 'OK' clicked
        if (dialog.showAndGet()) {
            generateClassFromJson(
                project = project,
                isFromJsonFile = dialog.isFromJsonFile(),
                className = dialog.getClassName(),
                packageName = dialog.getPackageName(),
                outputPath = dialog.getOutputPath(),
                jsonText = dialog.getJsonText(),
                inputJsonFilePath = dialog.getJsonFilePath(),
            )
        }
    }

    private fun generateClassFromJson(
        project: Project?,
        isFromJsonFile: Boolean,
        className: String,
        packageName: String,
        outputPath: String,
        jsonText: String,
        inputJsonFilePath: String,
    ) {
        try {
            val runnable =
                if (isFromJsonFile) getRunnableGenerateFromJsonFile(
                    project = project,
                    className = className,
                    packageName = packageName,
                    path = outputPath,
                    filePath = inputJsonFilePath,
                )
                else getRunnableGenerateFromJsonText(
                    project = project,
                    className = className,
                    packageName = packageName,
                    path = outputPath,
                    jsonText = jsonText,
                )

            writeFile(runnable)

            previousOutputPath = outputPath
        } catch (exception: Exception) {
            project.showNotification(
                title = StringResources.TITLE_JSON_TO_DATA_CLASS,
                content = "${StringResources.ERROR_GENERATE_JSON_TO_DATA_CLASS}\n${exception.localizedMessage}",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun getRunnableGenerateFromJsonText(
        project: Project?,
        className: String,
        packageName: String,
        path: String,
        jsonText: String,
    ): Runnable {
        return Runnable {
            addPsiFileToDirectory(
                project = project,
                virtualFile = File(path).toVirtualFile(),
                className = className,
                code = jsonText.toKotlinString(
                    packageName = packageName,
                    className = className,
                )
            )
        }
    }

    private fun getRunnableGenerateFromJsonFile(
        project: Project?,
        className: String,
        packageName: String,
        path: String,
        filePath: String,
    ): Runnable {
        return Runnable {
            val inputStream = Files.newInputStream(Path.of(filePath))
            addPsiFileToDirectory(
                project = project,
                virtualFile = File(path).toVirtualFile(),
                className = className,
                code = inputStream.convertToString().toKotlinString(
                    packageName = packageName,
                    className = className,
                )
            )
        }
    }

    private fun addPsiFileToDirectory(
        project: Project?,
        virtualFile: VirtualFile?,
        className: String?,
        code: String?,
    ) {
        if (virtualFile != null) {
            val psiFile = generateKotlinPsiFile(
                project = project,
                className = className,
                code = code,
            )

            PsiDirectoryFactory.getInstance(project)
                .createDirectory(virtualFile)
                .add(psiFile)
        } else {
            project.showNotification(
                title = StringResources.TITLE_JSON_TO_DATA_CLASS,
                content = StringResources.ERROR_GENERATE_JSON_TO_DATA_CLASS,
                type = NotificationType.ERROR,
            )
        }
    }

    private fun generateKotlinPsiFile(
        project: Project?,
        className: String?,
        code: String?,
    ): PsiFile {
        return PsiFileFactory
            .getInstance(project)
            .createFileFromText("${className}.kt", KotlinLanguage.INSTANCE, code.orEmpty())
    }

    private fun writeFile(runnable: Runnable) {
        if (applicationManager.isDispatchThread) {
            applicationManager.runWriteAction(runnable)
        } else {
            applicationManager.invokeLater {
                applicationManager.runWriteAction(runnable)
            }
        }
    }
}
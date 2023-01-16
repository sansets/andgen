package id.andgen.andgen.actions.image_picker

import com.android.tools.idea.gradle.project.sync.GradleSyncInvoker
import com.google.wireless.android.sdk.stats.GradleSyncStats
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import id.andgen.andgen.StringResources
import id.andgen.andgen.actions.image_picker.codes.*
import id.andgen.andgen.util.ext.showNotification
import id.andgen.andgen.util.ext.writeFile
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import java.io.File


class ImagePickerAction : AnAction() {

    private val applicationManager = ApplicationManager.getApplication()
    private var previousOutputPath = ""

    private lateinit var psiDirectoryFactory: PsiDirectoryFactory
    private lateinit var psiFileFactory: PsiFileFactory
    private lateinit var psiManager: PsiManager
    private lateinit var psiDocumentManager: PsiDocumentManager

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project

        if (!::psiDirectoryFactory.isInitialized) psiDirectoryFactory = PsiDirectoryFactory.getInstance(project)
        if (!::psiFileFactory.isInitialized) psiFileFactory = PsiFileFactory.getInstance(project)
        if (!::psiManager.isInitialized && project != null) psiManager = PsiManager.getInstance(project)
        if (!::psiDocumentManager.isInitialized && project != null) psiDocumentManager =
            PsiDocumentManager.getInstance(project)

        showGeneratorDialog(event.project)
    }

    private fun showGeneratorDialog(project: Project?) {
        val dialog = ImagePickerForm(
            project = project,
        )

        // Generate if button 'OK' clicked
        if (dialog.showAndGet()) {
            generateImagePickerLibrary(
                project = project,
                packageName = dialog.getPackageName(),
                libraryName = dialog.getLibraryName(),
                libraryDir = dialog.getLibraryDirPath(),
            )
        }
    }

    private fun generateImagePickerLibrary(
        project: Project?,
        packageName: String,
        libraryName: String,
        libraryDir: String,
    ) {
        try {
            createDirectory(libraryDir)
            createDirectory("$libraryDir/src")
            createDirectory("$libraryDir/src/androidTest")
            createDirectory("$libraryDir/src/main")
            createDirectory("$libraryDir/src/test")
            createDirectory("$libraryDir/src/androidTest/java")
            createDirectory("$libraryDir/src/main/java")
            createDirectory("$libraryDir/src/test/java")
            packageName.split(".").forEachIndexed { index, _ ->
                val path = packageName.split(".").take(index + 1).joinToString(separator = "/")

                createDirectory("$libraryDir/src/androidTest/java/$path")
                createDirectory("$libraryDir/src/main/java/$path")
                createDirectory("$libraryDir/src/test/java/$path")
            }

            val runnable = getRunnableGenerateImagePicker(
                project = project,
                packageName = packageName,
                libraryDir = libraryDir,
            )

            applicationManager.writeFile(runnable)

            updateProjectSettingGradle(
                project = project,
                libraryName = libraryName,
            )
            project?.let {
                GradleSyncInvoker.getInstance()
                    .requestProjectSync(it, GradleSyncStats.Trigger.TRIGGER_USER_SYNC_ACTION)
            }

            previousOutputPath = libraryDir
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_IMAGE_PICKER}",
                content = "${StringResources.ERROR_GENERATE_IMAGE_PICKER}\n${exception.localizedMessage}",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun getRunnableGenerateImagePicker(
        project: Project?,
        packageName: String,
        libraryDir: String,
    ): Runnable {
        return Runnable {
            addFiles(
                project = project,
                packageName = packageName,
                libraryDir = libraryDir,
            )
        }
    }

    private fun createDirectory(
        dir: String,
    ) {
        val directoryFile = File(dir)
        if (!directoryFile.exists()) {
            directoryFile.mkdir()
            VfsUtil.markDirtyAndRefresh(false, false, false, directoryFile)
        }
    }

    private fun addFiles(
        project: Project?,
        packageName: String,
        libraryDir: String,
    ) {
        val projectVirtualFile = File(libraryDir).toVirtualFile()
        val mainVirtualFile = File("$libraryDir/src/main").toVirtualFile()

        addPsiFileToDirectory(
            project = project,
            virtualFile = projectVirtualFile,
            fileName = "",
            extension = "gitignore",
            code = Gitignore.getCode(),
        )
        addPsiFileToDirectory(
            project = project,
            virtualFile = projectVirtualFile,
            fileName = "build",
            extension = "gradle",
            code = BuildGradle.getCode(packageName),
        )
        addPsiFileToDirectory(
            project = project,
            virtualFile = projectVirtualFile,
            fileName = "consumer-rules",
            extension = "pro",
            code = ConsumerRulesPro.getCode(),
        )
        addPsiFileToDirectory(
            project = project,
            virtualFile = projectVirtualFile,
            fileName = "proguard-rules",
            extension = "pro",
            code = ProguardRulesPro.getCode(),
        )
        addPsiFileToDirectory(
            project = project,
            virtualFile = mainVirtualFile,
            fileName = "AndroidManifest",
            extension = "xml",
            code = AndroidManifestXml.getCode(),
        )
    }

    private fun addPsiFileToDirectory(
        project: Project?,
        virtualFile: VirtualFile?,
        fileName: String?,
        extension: String?,
        code: String?,
    ) {
        if (virtualFile != null) {
            val psiFile = generatePsiFile(
                fileName = fileName,
                extension = extension,
                code = code,
            )

            psiDirectoryFactory
                .createDirectory(virtualFile)
                .add(psiFile)
        } else {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_IMAGE_PICKER}",
                content = "${StringResources.ERROR_CREATE_FILE} $fileName",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun generatePsiFile(
        fileName: String?,
        extension: String?,
        code: String?,
    ): PsiFile {
        return psiFileFactory
            .createFileFromText("$fileName.$extension", KotlinLanguage.INSTANCE, code.orEmpty())
    }

    private fun updateProjectSettingGradle(
        project: Project?,
        libraryName: String,
    ) {
        try {
            val runnable = Runnable {
                val virtualFile = File(project?.basePath + "/settings.gradle").toVirtualFile() ?: throw Exception()
                val file = psiManager.findFile(virtualFile) ?: throw Exception()
                val document = psiDocumentManager.getDocument(file) ?: throw Exception()

                document.setText(
                    file.text.trimIndent() + "\n" +
                            "include ':${libraryName.lowercase()}'"
                )
            }
            WriteCommandAction.runWriteCommandAction(project, runnable)
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_IMAGE_PICKER}",
                content = StringResources.ERROR_UPDATE_SETTING_GRADLE,
                type = NotificationType.ERROR,
            )
        }
    }
}
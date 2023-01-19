package id.andgen.andgen.actions.image_picker

import com.android.tools.idea.gradle.project.sync.GradleSyncInvoker
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
import id.andgen.andgen.actions.image_picker.codes.BuildGradle
import id.andgen.andgen.actions.image_picker.codes.ConsumerRulesPro
import id.andgen.andgen.actions.image_picker.codes.Gitignore
import id.andgen.andgen.actions.image_picker.codes.ProguardRulesPro
import id.andgen.andgen.actions.image_picker.codes.src.main.AndroidManifestXml
import id.andgen.andgen.actions.image_picker.codes.src.main.kotlin.ImagePickerDialogFragmentKotlin
import id.andgen.andgen.actions.image_picker.codes.src.res.drawable.BaselineClose24Xml
import id.andgen.andgen.actions.image_picker.codes.src.res.drawable.BaselinePhotoCamera24Xml
import id.andgen.andgen.actions.image_picker.codes.src.res.drawable.BaselinePhotoLibrary24Xml
import id.andgen.andgen.actions.image_picker.codes.src.res.layout.AppBarDialogXml
import id.andgen.andgen.actions.image_picker.codes.src.res.layout.DialogFragmentImagePickerXml
import id.andgen.andgen.actions.image_picker.codes.src.res.values.ColorsXml
import id.andgen.andgen.actions.image_picker.codes.src.res.values.StringsXml
import id.andgen.andgen.actions.image_picker.codes.src.res.values.ThemesXml
import id.andgen.andgen.actions.image_picker.codes.src.res.values_in.StringsInXml
import id.andgen.andgen.actions.image_picker.codes.src.res.values_night.ColorsNightXml
import id.andgen.andgen.util.ext.showNotification
import id.andgen.andgen.util.ext.writeFile
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import org.jetbrains.kotlin.utils.indexOfFirst
import java.io.File


class ImagePickerAction : AnAction() {

    private val applicationManager = ApplicationManager.getApplication()

    private var project: Project? = null
    private var projectPath = ""

    private lateinit var psiDirectoryFactory: PsiDirectoryFactory
    private lateinit var psiFileFactory: PsiFileFactory

    private var psiManager: PsiManager? = null
    private var psiDocumentManager: PsiDocumentManager? = null

    private lateinit var gradleSyncInvoker: GradleSyncInvoker

    override fun actionPerformed(event: AnActionEvent) {
        project = event.project
        projectPath = project?.basePath.orEmpty()

        if (!::psiDirectoryFactory.isInitialized) psiDirectoryFactory = PsiDirectoryFactory.getInstance(project)
        if (!::psiFileFactory.isInitialized) psiFileFactory = PsiFileFactory.getInstance(project)
        if (!::gradleSyncInvoker.isInitialized) gradleSyncInvoker = GradleSyncInvoker.getInstance()

        project?.let {
            if (psiManager == null) psiManager = PsiManager.getInstance(it)
            if (psiDocumentManager == null) psiDocumentManager = PsiDocumentManager.getInstance(it)
        }

        showGeneratorDialog(event.project)
    }

    private fun showGeneratorDialog(project: Project?) {
        val dialog = ImagePickerForm(
            project = project,
        )

        // Generate if button 'OK' clicked
        if (dialog.showAndGet()) {
            generateImagePickerLibrary(
                packageName = dialog.getPackageName(),
                libraryName = dialog.getLibraryName(),
                libraryDir = "$projectPath/${dialog.getLibraryPath()}/${dialog.getLibraryName()}".replace("//", "/"),
                libraryProjectPath = dialog.getLibraryPath(),
            )
        }
    }

    private fun generateImagePickerLibrary(
        packageName: String,
        libraryName: String,
        libraryDir: String,
        libraryProjectPath: String,
    ) {
        try {
            createDirectories(
                libraryDir = libraryDir,
                packageName = packageName,
            )

            applicationManager.writeFile(
                getRunnableGenerateImagePicker(
                    packageName = packageName,
                    libraryDir = libraryDir,
                )
            )

            addJitpackRepositoryToProject(project)

            updateProjectSettingGradle(
                libraryName = libraryName,
                libraryPath = libraryProjectPath,
            )
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_IMAGE_PICKER}",
                content = "${StringResources.ERROR_GENERATE_IMAGE_PICKER}\n${exception.localizedMessage}",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun getRunnableGenerateImagePicker(
        packageName: String,
        libraryDir: String,
    ): Runnable {
        return Runnable {
            addFiles(
                packageName = packageName,
                libraryDir = libraryDir,
            )
        }
    }

    private fun createDirectory(
        dir: String,
    ) {
        val dirNames = dir.split("/")
        dirNames.forEachIndexed { index, _ ->
            val newDir = dirNames.take(index + 1).joinToString(separator = "/")
            val directoryFile = File(newDir)

            if (!File(newDir).exists()) {
                directoryFile.mkdir()
                VfsUtil.markDirtyAndRefresh(false, false, false, directoryFile)
            }
        }
    }

    private fun createDirectories(
        libraryDir: String,
        packageName: String,
    ) {
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
        createDirectory("$libraryDir/src/main/res")
        createDirectory("$libraryDir/src/main/res/drawable")
        createDirectory("$libraryDir/src/main/res/layout")
        createDirectory("$libraryDir/src/main/res/values")
        createDirectory("$libraryDir/src/main/res/values-night")
        createDirectory("$libraryDir/src/main/res/values-in")
    }

    private fun addFiles(
        packageName: String,
        libraryDir: String,
    ) {
        val projectVirtualFile = File(libraryDir).toVirtualFile()
        val mainVirtualFile = File("$libraryDir/src/main").toVirtualFile()
        val valuesVirtualFile = File("$libraryDir/src/main/res/values").toVirtualFile()
        val valuesNightVirtualFile = File("$libraryDir/src/main/res/values-night").toVirtualFile()
        val valuesInVirtualFile = File("$libraryDir/src/main/res/values-in").toVirtualFile()
        val drawableVirtualFile = File("$libraryDir/src/main/res/drawable").toVirtualFile()
        val layoutVirtualFile = File("$libraryDir/src/main/res/layout").toVirtualFile()
        val kotlinVirtualFile = File("$libraryDir/src/main/java/${packageName.replace(".", "/")}").toVirtualFile()

        addPsiFileToDirectory(
            virtualFile = projectVirtualFile,
            fileName = Gitignore.FILE_NAME,
            extension = Gitignore.EXTENSION,
            code = Gitignore.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = projectVirtualFile,
            fileName = BuildGradle.FILE_NAME,
            extension = BuildGradle.EXTENSION,
            code = BuildGradle.getCode(packageName),
        )
        addPsiFileToDirectory(
            virtualFile = projectVirtualFile,
            fileName = ConsumerRulesPro.FILE_NAME,
            extension = ConsumerRulesPro.EXTENSION,
            code = ConsumerRulesPro.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = projectVirtualFile,
            fileName = ProguardRulesPro.FILE_NAME,
            extension = ProguardRulesPro.EXTENSION,
            code = ProguardRulesPro.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = mainVirtualFile,
            fileName = AndroidManifestXml.FILE_NAME,
            extension = AndroidManifestXml.EXTENSION,
            code = AndroidManifestXml.getCode(),
        )

        // Add values file
        addPsiFileToDirectory(
            virtualFile = valuesVirtualFile,
            fileName = ColorsXml.FILE_NAME,
            extension = ColorsXml.EXTENSION,
            code = ColorsXml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = valuesVirtualFile,
            fileName = StringsXml.FILE_NAME,
            extension = StringsXml.EXTENSION,
            code = StringsXml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = valuesVirtualFile,
            fileName = ThemesXml.FILE_NAME,
            extension = ThemesXml.EXTENSION,
            code = ThemesXml.getCode(),
        )

        // Add values night file
        addPsiFileToDirectory(
            virtualFile = valuesNightVirtualFile,
            fileName = ColorsNightXml.FILE_NAME,
            extension = ColorsNightXml.EXTENSION,
            code = ColorsNightXml.getCode(),
        )

        // Add values locale in file
        addPsiFileToDirectory(
            virtualFile = valuesInVirtualFile,
            fileName = StringsInXml.FILE_NAME,
            extension = StringsInXml.EXTENSION,
            code = StringsInXml.getCode(),
        )

        // Add drawable file
        addPsiFileToDirectory(
            virtualFile = drawableVirtualFile,
            fileName = BaselineClose24Xml.FILE_NAME,
            extension = BaselineClose24Xml.EXTENSION,
            code = BaselineClose24Xml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = drawableVirtualFile,
            fileName = BaselinePhotoCamera24Xml.FILE_NAME,
            extension = BaselinePhotoCamera24Xml.EXTENSION,
            code = BaselinePhotoCamera24Xml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = drawableVirtualFile,
            fileName = BaselinePhotoLibrary24Xml.FILE_NAME,
            extension = BaselinePhotoLibrary24Xml.EXTENSION,
            code = BaselinePhotoLibrary24Xml.getCode(),
        )

        // Add layout file
        addPsiFileToDirectory(
            virtualFile = layoutVirtualFile,
            fileName = AppBarDialogXml.FILE_NAME,
            extension = AppBarDialogXml.EXTENSION,
            code = AppBarDialogXml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = layoutVirtualFile,
            fileName = DialogFragmentImagePickerXml.FILE_NAME,
            extension = DialogFragmentImagePickerXml.EXTENSION,
            code = DialogFragmentImagePickerXml.getCode(),
        )

        // Add kotlin file
        addPsiFileToDirectory(
            virtualFile = kotlinVirtualFile,
            fileName = ImagePickerDialogFragmentKotlin.FILE_NAME,
            extension = ImagePickerDialogFragmentKotlin.EXTENSION,
            code = ImagePickerDialogFragmentKotlin.getCode(packageName),
        )
    }

    private fun addPsiFileToDirectory(
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
        libraryName: String,
        libraryPath: String,
    ) {
        try {
            val runnable = Runnable {
                val virtualFile = File("$projectPath/settings.gradle").toVirtualFile() ?: throw Exception()
                val file = psiManager?.findFile(virtualFile) ?: throw Exception()
                val libraryModule = if (libraryPath.isEmpty())
                    "include ':$libraryName'"
                else
                    "include '${libraryPath.replace("/", ":")}:$libraryName'"

                if (!file.text.contains(libraryModule)) {
                    val document = psiDocumentManager?.getDocument(file) ?: throw Exception()

                    document.setText(file.text.trimIndent() + "\n" + libraryModule)
                }
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

    private fun addJitpackRepositoryToProject(project: Project?) {
        try {
            val runnable = Runnable {
                val settingGradleVirtualFile =
                    File(project?.basePath + "/settings.gradle").toVirtualFile() ?: throw Exception()
                val settingGradleFile = psiManager?.findFile(settingGradleVirtualFile) ?: throw Exception()
                val settingGradleDocument = psiDocumentManager?.getDocument(settingGradleFile) ?: throw Exception()

                if (settingGradleFile.text.contains("dependencyResolutionManagement", true)) {
                    val codeLines = settingGradleFile.text.split("\n")
                    val dependencyResolutionManagementIndex =
                        codeLines.indexOfFirst { it.contains("dependencyResolutionManagement") }
                    val repositoriesIndex = codeLines.indexOfFirst(
                        startFrom = dependencyResolutionManagementIndex,
                        predicate = { it.contains("repositories") })
                    val lastRepositoriesIndex = codeLines.indexOfFirst(
                        startFrom = repositoriesIndex,
                        predicate = { it.contains("}") })

                    val editedCodeLines = ArrayList<String>().apply {
                        addAll(settingGradleFile.text.split("\n"))
                        add(lastRepositoriesIndex, "        maven { url \"https://jitpack.io\" }")
                    }
                    val editedCode = editedCodeLines.joinToString(separator = "\n")

                    if (!codeLines.any { it.contains("https://jitpack.io") }) {
                        settingGradleDocument.setText(editedCode)
                    }
                } else {
                    val buildGradleVirtualFile =
                        File(project?.basePath + "/build.gradle").toVirtualFile()
                            ?: File(project?.basePath + "/build.gradle.kts").toVirtualFile()
                            ?: throw Exception()

                    val buildGradleFile = psiManager?.findFile(buildGradleVirtualFile) ?: throw Exception()
                    val buildGradleDocument = psiDocumentManager?.getDocument(settingGradleFile) ?: throw Exception()

                    val codeLines = buildGradleFile.text.split("\n")
                    val allprojectsIndex =
                        codeLines.indexOfFirst { it.contains("allprojects") }
                    val repositoriesIndex = codeLines.indexOfFirst(
                        startFrom = allprojectsIndex,
                        predicate = { it.contains("repositories") })
                    val lastRepositoriesIndex = codeLines.indexOfFirst(
                        startFrom = repositoriesIndex,
                        predicate = { it.contains("}") })

                    val editedCodeLines = ArrayList<String>().apply {
                        addAll(buildGradleFile.text.split("\n"))
                        add(lastRepositoriesIndex, "        maven { url \"https://jitpack.io\" }")
                    }
                    val editedCode = editedCodeLines.joinToString(separator = "\n")

                    if (!codeLines.any { it.contains("https://jitpack.io") }) {
                        buildGradleDocument.setText(editedCode)
                    }
                }
            }

            WriteCommandAction.runWriteCommandAction(project, runnable)
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_IMAGE_PICKER}",
                content = StringResources.ERROR_ADD_JITPACK_REPOSITORY,
                type = NotificationType.ERROR,
            )
        }
    }
}
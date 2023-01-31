package id.andgen.andgen.actions.barcode_scanner

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
import id.andgen.andgen.actions.barcode_scanner.codes.BuildGradle
import id.andgen.andgen.actions.barcode_scanner.codes.ConsumerRulesPro
import id.andgen.andgen.actions.barcode_scanner.codes.Gitignore
import id.andgen.andgen.actions.barcode_scanner.codes.ProguardRulesPro
import id.andgen.andgen.actions.barcode_scanner.codes.src.main.AndroidManifestXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin.BarcodeScannerActivityKotlin
import id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin.BarcodeScannerFragmentKotlin
import id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin.util.ext.FragmentExtKotlin
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.drawable.BaselineArrowBack24Xml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.layout.ActivityBarcodeScannerXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.layout.FragmentBarcodeScannerXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.values.ColorsXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.values.StringsXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.values.ThemesXml
import id.andgen.andgen.actions.barcode_scanner.codes.src.res.values_in.StringsInXml
import id.andgen.andgen.util.ext.showNotification
import id.andgen.andgen.util.ext.writeFile
import org.apache.commons.io.FileUtils
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import java.io.File


class BarcodeScannerAction : AnAction() {

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
        val dialog = BarcodeScannerForm(
            project = project,
        )

        // Generate if button 'OK' clicked
        if (dialog.showAndGet()) {
            generateBarcodeScannerLibrary(
                packageName = dialog.getPackageName(),
                libraryName = dialog.getLibraryName(),
                libraryDir = "$projectPath/${dialog.getLibraryPath()}/${dialog.getLibraryName()}"
                    .split("/")
                    .filter { it.isNotEmpty() }
                    .joinToString("/"),
                libraryProjectPath = dialog.getLibraryPath()
                    .split("/")
                    .filter { it.isNotEmpty() }
                    .joinToString("/"),
                minimumSdkVersion = dialog.getMinimSdkVersion(),
            )
        }
    }

    private fun generateBarcodeScannerLibrary(
        packageName: String,
        libraryName: String,
        libraryDir: String,
        libraryProjectPath: String,
        minimumSdkVersion: Int,
    ) {
        try {
            createDirectories(
                libraryDir = libraryDir,
                packageName = packageName,
            )

            applicationManager.writeFile(
                getRunnableGenerateBarcodeScanner(
                    packageName = packageName,
                    libraryDir = libraryDir,
                    minimumSdkVersion = minimumSdkVersion,
                )
            )

            updateProjectSettingGradle(
                libraryName = libraryName,
                libraryPath = libraryProjectPath,
            )
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_BARCODE_SCANNER}",
                content = "${StringResources.ERROR_GENERATE_BARCODE_SCANNER}\n${exception.localizedMessage}",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun getRunnableGenerateBarcodeScanner(
        packageName: String,
        libraryDir: String,
        minimumSdkVersion: Int,
    ): Runnable {
        return Runnable {
            addFiles(
                packageName = packageName,
                libraryDir = libraryDir,
                minimumSdkVersion = minimumSdkVersion,
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
        createDirectory("$libraryDir/src/main/java/${packageName.replace(".", "/")}/util")
        createDirectory("$libraryDir/src/main/java/${packageName.replace(".", "/")}/util/ext")
        createDirectory("$libraryDir/src/main/res")
        createDirectory("$libraryDir/src/main/res/drawable")
        createDirectory("$libraryDir/src/main/res/layout")
        createDirectory("$libraryDir/src/main/res/values")
        createDirectory("$libraryDir/src/main/res/values-in")
    }

    private fun addFiles(
        packageName: String,
        libraryDir: String,
        minimumSdkVersion: Int,
    ) {
        val projectVirtualFile = File(libraryDir).toVirtualFile()
        val mainVirtualFile = File("$libraryDir/src/main").toVirtualFile()
        val valuesVirtualFile = File("$libraryDir/src/main/res/values").toVirtualFile()
        val valuesInVirtualFile = File("$libraryDir/src/main/res/values-in").toVirtualFile()
        val drawableVirtualFile = File("$libraryDir/src/main/res/drawable").toVirtualFile()
        val layoutVirtualFile = File("$libraryDir/src/main/res/layout").toVirtualFile()
        val kotlinVirtualFile = File("$libraryDir/src/main/java/${packageName.replace(".", "/")}").toVirtualFile()
        val utilExtVirtualFile =
            File("$libraryDir/src/main/java/${packageName.replace(".", "/")}/util/ext").toVirtualFile()

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
            code = BuildGradle.getCode(
                packageName = packageName,
                minimumSdkVersion = minimumSdkVersion,
            ),
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
            fileName = BaselineArrowBack24Xml.FILE_NAME,
            extension = BaselineArrowBack24Xml.EXTENSION,
            code = BaselineArrowBack24Xml.getCode(),
        )
        addImageFileToDirectory(
            virtualFile = drawableVirtualFile,
            fileName = "bg_scanner_frame",
            extension = "webp",
        )

        // Add layout file
        addPsiFileToDirectory(
            virtualFile = layoutVirtualFile,
            fileName = FragmentBarcodeScannerXml.FILE_NAME,
            extension = FragmentBarcodeScannerXml.EXTENSION,
            code = FragmentBarcodeScannerXml.getCode(),
        )
        addPsiFileToDirectory(
            virtualFile = layoutVirtualFile,
            fileName = ActivityBarcodeScannerXml.FILE_NAME,
            extension = ActivityBarcodeScannerXml.EXTENSION,
            code = ActivityBarcodeScannerXml.getCode(),
        )

        // Add kotlin file
        addPsiFileToDirectory(
            virtualFile = kotlinVirtualFile,
            fileName = BarcodeScannerFragmentKotlin.FILE_NAME,
            extension = BarcodeScannerFragmentKotlin.EXTENSION,
            code = BarcodeScannerFragmentKotlin.getCode(packageName),
        )
        addPsiFileToDirectory(
            virtualFile = kotlinVirtualFile,
            fileName = BarcodeScannerActivityKotlin.FILE_NAME,
            extension = BarcodeScannerActivityKotlin.EXTENSION,
            code = BarcodeScannerActivityKotlin.getCode(packageName),
        )
        addPsiFileToDirectory(
            virtualFile = utilExtVirtualFile,
            fileName = FragmentExtKotlin.FILE_NAME,
            extension = FragmentExtKotlin.EXTENSION,
            code = FragmentExtKotlin.getCode(packageName),
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
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_BARCODE_SCANNER}",
                content = "${StringResources.ERROR_CREATE_FILE} $fileName",
                type = NotificationType.ERROR,
            )
        }
    }

    private fun addImageFileToDirectory(
        virtualFile: VirtualFile?,
        fileName: String,
        extension: String,
    ) {
        try {
            if (virtualFile == null) throw Exception()

            println(fileName)

            val imageFile = File("$fileName.$extension")
            FileUtils.copyToFile(javaClass.getResourceAsStream("/drawable/bg_scanner_frame.webp"), imageFile)
            val psiFile = imageFile.toPsiFile(project ?: throw Exception()) ?: throw Exception()

            psiDirectoryFactory
                .createDirectory(virtualFile)
                .add(psiFile)
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_BARCODE_SCANNER}",
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
                    "include ':${libraryPath.replace("/", ":")}:$libraryName'"

                if (!file.text.contains(libraryModule)) {
                    val document = psiDocumentManager?.getDocument(file) ?: throw Exception()

                    document.setText(file.text.trimIndent() + "\n" + libraryModule)
                }
            }
            WriteCommandAction.runWriteCommandAction(project, runnable)
        } catch (exception: Exception) {
            project.showNotification(
                title = "${StringResources.TITLE_APP_NAME}: ${StringResources.TITLE_BARCODE_SCANNER}",
                content = StringResources.ERROR_UPDATE_SETTING_GRADLE,
                type = NotificationType.ERROR,
            )
        }
    }
}
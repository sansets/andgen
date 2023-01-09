package id.andgen.andgen.actions.json_to_data_class

import com.intellij.json.JsonLanguage
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.vfs.VirtualFile
import id.andgen.andgen.StringResources
import id.andgen.andgen.util.listener.OnChangedDocumentListener
import id.andgen.jsontokotlin.ext.toPackageName
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import java.io.File
import javax.swing.*
import javax.swing.event.DocumentEvent

class JsonToDataClassForm(
    private val project: Project?,
    private val previousOutputPath: String = ""
) : DialogWrapper(project) {
    private lateinit var plJsonToDataClass: JPanel
    private lateinit var tfClassName: JTextField
    private lateinit var tfPackageName: JTextField
    private lateinit var tfOutputPath: TextFieldWithBrowseButton
    private lateinit var cbUseJsonFile: JCheckBox
    private lateinit var lbJsonFilePath: JLabel
    private lateinit var plJsonFilePath: JPanel
    private lateinit var tfJsonFilePath: JTextField
    private lateinit var btBrowseJsonFile: JButton
    private lateinit var taJsonString: JTextArea
    private lateinit var plJsonText: JPanel
    private lateinit var lbJsonText: JLabel
    private lateinit var panSpacer: JPanel

    private var currentSelectedInputJsonFile: VirtualFile? = null

    init {
        title = StringResources.TITLE_JSON_TO_DATA_CLASS

        init()
        setDefaultOutputDirectory()
        initFileOutputIconOnClickListener()
        initFileOutputOnChangedListener()
        initDataClassNameOnChangedListener()
        initCheckBoxOnCheckedListener()
        initFileOutputButtonOnBrowseListener()
    }

    override fun createCenterPanel(): JComponent {
        return plJsonToDataClass
    }

    fun getClassName(): String {
        return tfClassName.text.orEmpty()
    }

    fun getPackageName(): String {
        return tfPackageName.text.orEmpty()
    }

    fun getOutputPath(): String {
        return tfOutputPath.text.replace("${getClassName()}.kt", "")
    }

    fun isFromJsonFile(): Boolean {
        return cbUseJsonFile.isSelected
    }

    fun getJsonText(): String {
        return taJsonString.text.orEmpty()
    }

    fun getJsonFilePath(): String {
        return tfJsonFilePath.text.orEmpty()
    }

    private fun setDefaultOutputDirectory() {
        if (previousOutputPath.isNotEmpty()) {
            if (previousOutputPath.last() != '/') {
                tfOutputPath.text = "$previousOutputPath/"
            } else {
                tfOutputPath.text = previousOutputPath
            }
        } else {
            tfOutputPath.text = "${project?.basePath.orEmpty()}/"
        }
    }

    private fun initDataClassNameOnChangedListener() {
        tfClassName.document?.addDocumentListener(object : OnChangedDocumentListener {
            override fun onUpdated(event: DocumentEvent?) {
                setOutputPathClassName(tfClassName.text)
            }
        })
    }

    private fun initFileOutputIconOnClickListener() {
        tfOutputPath.addActionListener {
            var virtualFile: VirtualFile? = null

            try {
                val path = if (tfOutputPath.text.isNotEmpty()) {
                    tfOutputPath.text.replace("${tfClassName.text.orEmpty()}.kt", "")
                } else {
                    tfOutputPath.text
                }

                virtualFile = File(path).toVirtualFile()
            } catch (e: Exception) {
                println(e.stackTrace)
            }

            val selectedDirectory = FileChooser.chooseFile(
                FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                tfOutputPath,
                project,
                virtualFile,
            )

            if (selectedDirectory != null) {
                if (tfClassName.text.isNullOrEmpty()) {
                    tfOutputPath.text = "${selectedDirectory.path}/"
                } else {
                    tfOutputPath.text = "${selectedDirectory.path}/${tfClassName.text}.kt"
                }
            }
        }
    }

    private fun initFileOutputOnChangedListener() {
        tfOutputPath.textField.document?.addDocumentListener(object : OnChangedDocumentListener {
            override fun onUpdated(event: DocumentEvent?) {
                tfPackageName.text = getOutputPath().toPackageName()
            }
        })
    }

    private fun setOutputPathClassName(text: String?) {
        if (tfOutputPath.text.last() != '/' && tfOutputPath.text.takeLast(3) != ".kt") {
            tfOutputPath.text = "${tfOutputPath.text}/"
        }

        tfOutputPath.text = tfOutputPath.text.replaceAfterLast("/", "")

        if (!text.isNullOrEmpty()) {
            tfOutputPath.text = "${tfOutputPath.text}$text.kt"
        } else {
            tfOutputPath.text = tfOutputPath.text + text
        }
    }

    private fun initCheckBoxOnCheckedListener() {
        setJsonFilePathFieldVisibility(false)
        setJsonFileTextFieldVisibility(true)

        cbUseJsonFile.addActionListener {
            if (cbUseJsonFile.isSelected) {
                setJsonFilePathFieldVisibility(true)
                setJsonFileTextFieldVisibility(false)
            } else {
                setJsonFilePathFieldVisibility(false)
                setJsonFileTextFieldVisibility(true)
            }
        }
    }

    private fun setJsonFilePathFieldVisibility(isVisible: Boolean) {
        plJsonFilePath.isVisible = isVisible
        lbJsonFilePath.isVisible = isVisible
        panSpacer.isVisible = isVisible
    }

    private fun setJsonFileTextFieldVisibility(isVisible: Boolean) {
        plJsonText.isVisible = isVisible
        lbJsonText.isVisible = isVisible
        panSpacer.isVisible = !isVisible
    }

    private fun initFileOutputButtonOnBrowseListener() {
        btBrowseJsonFile.addActionListener {
            val selectedFile = FileChooser.chooseFile(
                FileChooserDescriptorFactory.createSingleFileDescriptor(JsonLanguage.INSTANCE.id),
                tfJsonFilePath,
                project,
                currentSelectedInputJsonFile,
            )

            if (selectedFile != null) {
                tfJsonFilePath.text = selectedFile.path
                currentSelectedInputJsonFile = selectedFile
            }
        }
    }
}
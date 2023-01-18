package id.andgen.andgen.actions.image_picker

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import id.andgen.andgen.StringResources
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class ImagePickerForm(
    private val project: Project?
) : DialogWrapper(project) {

    companion object {
        private const val DEFAULT_LIBRARY_NAME = "ImagePicker"
        private const val DEFAULT_PACKAGE_NAME = "com.example.imagepicker"
    }

    private lateinit var plJsonToDataClass: JPanel
    private lateinit var tfLibraryName: JTextField
    private lateinit var tfPackageName: JTextField
    private lateinit var tfLibraryDir: TextFieldWithBrowseButton

    init {
        title = StringResources.TITLE_CREATE_IMAGE_PICKER_LIBRARY

        init()
        setDefaultLibraryName()
        setDefaultPackageName()
        setDefaultLibraryDirectory()
    }

    override fun createCenterPanel(): JComponent {
        return plJsonToDataClass
    }

    fun getLibraryName(): String {
        return tfLibraryName.text.orEmpty()
    }

    fun getPackageName(): String {
        return tfPackageName.text.orEmpty()
    }

    fun getLibraryDirPath(): String {
        return tfLibraryDir.text.replace(getLibraryName(), "")
    }

    private fun setDefaultLibraryName() {
        tfLibraryName.text = DEFAULT_LIBRARY_NAME
    }

    private fun setDefaultPackageName() {
        tfPackageName.text = DEFAULT_PACKAGE_NAME
    }

    private fun setDefaultLibraryDirectory() {
        tfLibraryDir.text = "${project?.basePath.orEmpty()}/${getLibraryName().lowercase()}"
    }
}
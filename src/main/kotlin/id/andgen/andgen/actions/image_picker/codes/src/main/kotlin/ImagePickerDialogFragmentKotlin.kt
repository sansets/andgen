package id.andgen.andgen.actions.image_picker.codes.src.main.kotlin

object ImagePickerDialogFragmentKotlin {

    const val FILE_NAME = "ImagePickerDialogFragment"
    const val EXTENSION = "kt"

    fun getCode(packageName: String): String {
        return """
            package $packageName

            import android.app.Activity
            import android.content.Intent
            import android.net.Uri
            import android.os.Bundle
            import android.view.LayoutInflater
            import android.view.View
            import android.view.ViewGroup
            import androidx.activity.result.ActivityResult
            import androidx.activity.result.ActivityResultLauncher
            import androidx.activity.result.contract.ActivityResultContracts
            import androidx.fragment.app.Fragment
            import androidx.fragment.app.FragmentManager
            import $packageName.databinding.DialogFragmentImagePickerBinding
            import com.github.dhaval2404.imagepicker.ImagePicker
            import com.google.android.material.bottomsheet.BottomSheetDialogFragment
            
            
            /**
             * A simple [Fragment] subclass.
             * Use the [ImagePickerDialogFragment.newInstance] factory method to
             * create an instance of this fragment.
             */
            class ImagePickerDialogFragment : BottomSheetDialogFragment() {
            
                var onResultSuccess: ((imageUri: Uri) -> Unit)? = null
                var onResultError: ((error: String) -> Unit)? = null
            
                private var _binding: DialogFragmentImagePickerBinding? = null
                private val binding get() = _binding!!
            
                private lateinit var startForProfileImageResult: ActivityResultLauncher<Intent>
            
                override fun onCreateView(
                    inflater: LayoutInflater, container: ViewGroup?,
                    savedInstanceState: Bundle?,
                ): View {
                    // Inflate the layout for this fragment
                    _binding = DialogFragmentImagePickerBinding.inflate(layoutInflater)
                    return binding.root
                }
            
                override fun onDestroyView() {
                    super.onDestroyView()
                    _binding = null
                }
            
                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)
            
                    initView()
                    initViewListener()
                }
            
                private fun initView() {
                    binding.layoutAppBar.toolbar.title = getString(R.string.title_select_image)
                }
            
                private fun initViewListener() {
                    binding.layoutAppBar.btnClose.setOnClickListener {
                        dismiss()
                    }
                    binding.btnCamera.setOnClickListener {
                        ImagePicker.with(this)
                            .cameraOnly()
                            .createIntent {
                                startForProfileImageResult.launch(it)
                            }
                    }
                    binding.btnGallery.setOnClickListener {
                        ImagePicker.with(this)
                            .galleryOnly()
                            .createIntent {
                                startForProfileImageResult.launch(it)
                            }
                    }
                }
            
                fun showChooser(fragmentManager: FragmentManager) {
                    startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                        val resultCode = result.resultCode
                        val data = result.data
            
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                data?.data?.let {
                                    onResultSuccess?.invoke(it)
                                }
                                dismiss()
                            }
                            ImagePicker.RESULT_ERROR -> {
                                onResultError?.invoke(ImagePicker.getError(data))
                            }
                        }
                    }
            
                    show(fragmentManager, TAG)
                }
            
                companion object {
                    const val TAG = "image_chooser"
            
                    /**
                     * Use this factory method to create a new instance of
                     *
                     * @return A new instance of fragment ImageChooserFragment.
                     */
                    @JvmStatic
                    fun newInstance() = ImagePickerDialogFragment()
                }
            }
        """.trimIndent()
    }
}
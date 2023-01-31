package id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin

object BarcodeScannerFragmentKotlin {

    const val FILE_NAME = "BarcodeScannerFragment"
    const val EXTENSION = "kt"

    fun getCode(packageName: String): String {
        return """
            package $packageName

            import android.Manifest
            import android.os.Bundle
            import android.view.LayoutInflater
            import android.view.View
            import android.view.ViewGroup
            import android.widget.Toast
            import androidx.fragment.app.Fragment
            import $packageName.databinding.FragmentBarcodeScannerBinding
            import $packageName.util.ext.hasCameraPermission
            import $packageName.util.ext.requestSinglePermission
            import com.google.zxing.BarcodeFormat
            import com.google.zxing.client.android.BeepManager
            import com.journeyapps.barcodescanner.BarcodeCallback
            import com.journeyapps.barcodescanner.BarcodeResult
            import com.journeyapps.barcodescanner.DefaultDecoderFactory
            import com.karumi.dexter.PermissionToken
            import com.karumi.dexter.listener.PermissionDeniedResponse
            import com.karumi.dexter.listener.PermissionGrantedResponse
            import com.karumi.dexter.listener.PermissionRequest
            import com.karumi.dexter.listener.single.PermissionListener
            
            
            class BarcodeScannerFragment : Fragment(), PermissionListener, BarcodeCallback {
            
                private var _binding: FragmentBarcodeScannerBinding? = null
                private val binding get() = _binding!!
            
                private var beepManager: BeepManager? = null
            
                override fun onCreateView(
                    inflater: LayoutInflater, container: ViewGroup?,
                    savedInstanceState: Bundle?,
                ): View {
                    // Inflate the layout for this fragment
                    _binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false)
                    return binding.root
                }
            
                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)
            
                    initView()
                    initViewListener()
            
                    if (!hasCameraPermission()) {
                        requestSinglePermission(Manifest.permission.CAMERA, this)
                    } else {
                        initBarcodeScanner()
                    }
                }
            
                override fun onResume() {
                    super.onResume()
            
                    if (hasCameraPermission()) {
                        // Enable scanner to scan QRCode when camera permission has granted
                        binding.viewBarcode.resume()
                    }
                }
            
                /**
                 * Handle fragment onPause
                 *
                 */
                override fun onPause() {
                    super.onPause()
            
                    // Prevent scanner scan QRCode onPause fragment
                    binding.viewBarcode.pause()
                }
            
            
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    response?.let {
                        when (it.permissionName) {
                            Manifest.permission.CAMERA -> {
                                initBarcodeScanner()
                            }
                        }
                    }
                }
            
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    when (response?.permissionName) {
                        Manifest.permission.CAMERA -> {
                            Toast.makeText(
                                context,
                                getString(R.string.text_camera_access_required),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            
                override fun onPermissionRationaleShouldBeShown(
                    response: PermissionRequest?,
                    token: PermissionToken?,
                ) {
                    token?.continuePermissionRequest()
                }
            
                override fun barcodeResult(result: BarcodeResult?) {
                    beepManager?.playBeepSoundAndVibrate()
            
                    Toast.makeText(
                        context,
                        result?.text,
                        Toast.LENGTH_LONG
                    ).show()
                }
                
                private fun initView() {
                    binding.viewBarcode.setStatusText("")
                }
            
                private fun initViewListener() {
                    binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
                }
            
                /**
                 * Setup QRScanner on initialization
                 */
                private fun initBarcodeScanner() {
                    val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
                    binding.viewBarcode.apply {
                        barcodeView.decoderFactory = DefaultDecoderFactory(formats)
                        decodeContinuous(this@BarcodeScannerFragment)
                        setStatusText("")
                    }
            
                    beepManager = BeepManager(requireActivity())
                    beepManager?.isBeepEnabled = true
                    beepManager?.isVibrateEnabled = true
                }
            
                companion object {
                    /**
                     * Use this factory method to create a new instance of
                     * this fragment using the provided parameters.
                     *
                     * @return A new instance of fragment BarcodeScannerFragment.
                     */
                    @JvmStatic
                    fun newInstance() = BarcodeScannerFragment()
                }
            }
        """.trimIndent()
    }
}
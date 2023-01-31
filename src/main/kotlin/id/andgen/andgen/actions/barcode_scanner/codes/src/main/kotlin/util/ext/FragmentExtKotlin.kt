package id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin.util.ext

object FragmentExtKotlin {

    const val FILE_NAME = "FragmentExt"
    const val EXTENSION = "kt"

    fun getCode(packageName: String): String {
        return """
            package $packageName.util.ext

            import android.Manifest
            import android.content.pm.PackageManager
            import androidx.core.content.ContextCompat
            import androidx.fragment.app.Fragment
            import com.karumi.dexter.Dexter
            import com.karumi.dexter.listener.single.PermissionListener
            
            fun Fragment.hasCameraPermission(): Boolean =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            
            fun Fragment.requestSinglePermission(
                permission: String,
                listener: PermissionListener,
                isSameThread: Boolean = true
            ) {
                Dexter.withContext(requireContext())
                    .withPermission(permission)
                    .withListener(listener)
                    .apply { if (isSameThread) onSameThread() }
                    .check()
            }
        """.trimIndent()
    }
}
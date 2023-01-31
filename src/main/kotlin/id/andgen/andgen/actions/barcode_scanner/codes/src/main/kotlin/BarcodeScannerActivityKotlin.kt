package id.andgen.andgen.actions.barcode_scanner.codes.src.main.kotlin

object BarcodeScannerActivityKotlin {

    const val FILE_NAME = "BarcodeScannerActivity"
    const val EXTENSION = "kt"

    fun getCode(packageName: String): String {
        return """
            package $packageName

            import androidx.appcompat.app.AppCompatActivity
            import android.os.Bundle
            import $packageName.databinding.ActivityBarcodeScannerBinding
            
            class BarcodeScannerActivity : AppCompatActivity() {
            
                private lateinit var binding: ActivityBarcodeScannerBinding
            
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
            
                    binding = ActivityBarcodeScannerBinding.inflate(layoutInflater)
                    setContentView(binding.root)
            
                    inflateFragment()
                }
            
                private fun inflateFragment() {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.container.id, BarcodeScannerFragment.newInstance())
                        .commit()
                }
            }
        """.trimIndent()
    }
}
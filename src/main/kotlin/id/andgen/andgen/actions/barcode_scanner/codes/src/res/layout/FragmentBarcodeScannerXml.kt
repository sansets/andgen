package id.andgen.andgen.actions.barcode_scanner.codes.src.res.layout

object FragmentBarcodeScannerXml {

    const val FILE_NAME = "fragment_barcode_scanner"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                tools:context=".BarcodeScannerFragment">
            
                <com.journeyapps.barcodescanner.DecoratedBarcodeView
                    android:id="@+id/view_barcode"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    app:zxing_framing_rect_height="280dp"
                    app:zxing_framing_rect_width="280dp" />
            
                <androidx.coordinatorlayout.widget.CoordinatorLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="@color/colorBlack25">
            
                    <com.google.android.material.appbar.AppBarLayout
                        android:id="@+id/app_bar"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:backgroundTint="@android:color/transparent"
                        app:elevation="0dp">
            
                        <com.google.android.material.appbar.MaterialToolbar
                            android:id="@+id/toolbar"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            app:navigationIcon="@drawable/baseline_arrow_back_24"
                            app:navigationIconTint="@android:color/white"
                            app:titleTextColor="@android:color/white"
                            tools:title="Barcode Scanner" />
            
                    </com.google.android.material.appbar.AppBarLayout>
            
                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:id="@+id/layout_scanner"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">
            
                        <ImageView
                            android:id="@+id/view_square"
                            android:layout_width="280dp"
                            android:layout_height="280dp"
                            android:src="@drawable/bg_scanner_frame"
                            app:layout_constraintBottom_toBottomOf="parent"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent"
                            tools:ignore="ContentDescription" />
            
                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginStart="84dp"
                            android:layout_marginTop="56dp"
                            android:layout_marginEnd="84dp"
                            android:gravity="center"
                            android:text="@string/hint_scanner"
                            android:textColor="@android:color/white"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toBottomOf="@id/view_square" />
            
                    </androidx.constraintlayout.widget.ConstraintLayout>
            
                </androidx.coordinatorlayout.widget.CoordinatorLayout>
            
            </RelativeLayout>
        """.trimIndent()
    }
}
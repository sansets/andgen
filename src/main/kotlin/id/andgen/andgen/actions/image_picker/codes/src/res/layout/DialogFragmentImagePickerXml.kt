package id.andgen.andgen.actions.image_picker.codes.src.res.layout

object DialogFragmentImagePickerXml {

    const val FILE_NAME = "dialog_fragment_image_picker"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                android:id="@+id/bottom_sheet_image_chooser"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@color/colorBackground"
                android:orientation="vertical"
                app:layout_behavior="@string/bottom_sheet_behavior">
            
                <com.google.android.material.card.MaterialCardView
                    android:id="@+id/bottom_sheet_indicator"
                    style="@style/Theme.ImagePicker.CardView.BottomSheetIndicator"
                    android:layout_gravity="center"
                    android:layout_marginTop="16dp"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />
            
                <include
                    android:id="@+id/layout_app_bar"
                    layout="@layout/app_bar_dialog" />
            
                <com.google.android.material.button.MaterialButton
                    android:id="@+id/btn_camera"
                    style="@style/Theme.ImagePicker.Button.Nude"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="8dp"
                    android:gravity="start|center_vertical"
                    android:paddingStart="24dp"
                    android:paddingEnd="24dp"
                    android:text="@string/title_camera"
                    android:textColor="@color/colorTextPrimary"
                    app:cornerRadius="0dp"
                    app:icon="@drawable/baseline_photo_camera_24"
                    app:iconGravity="textStart"
                    app:iconPadding="16dp"
                    app:iconTint="@color/colorIcon" />
            
                <com.google.android.material.button.MaterialButton
                    android:id="@+id/btn_gallery"
                    style="@style/Theme.ImagePicker.Button.Nude"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="24dp"
                    android:gravity="start|center_vertical"
                    android:paddingStart="24dp"
                    android:paddingEnd="24dp"
                    android:text="@string/title_gallery"
                    android:textColor="@color/colorTextPrimary"
                    app:cornerRadius="0dp"
                    app:icon="@drawable/baseline_photo_library_24"
                    app:iconGravity="textStart"
                    app:iconPadding="16dp"
                    app:iconTint="@color/colorIcon" />
            
            </LinearLayout>
        """.trimIndent()
    }
}
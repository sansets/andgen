package id.andgen.andgen.actions.image_picker.codes.src.res.layout

object AppBarDialogXml {

    const val FILE_NAME = "app_bar_dialog"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <com.google.android.material.appbar.AppBarLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:backgroundTint="@color/colorBackground"
                app:elevation="0dp">
            
                <com.google.android.material.appbar.MaterialToolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?attr/actionBarSize"
                    app:contentInsetStartWithNavigation="0dp"
                    app:layout_collapseMode="pin"
                    app:title=""
                    app:titleCentered="true"
                    app:titleTextAppearance="@style/TextView.ToolbarTitle"
                    tools:title="Title">
            
                    <com.google.android.material.button.MaterialButton
                        android:id="@+id/btn_close"
                        style="@style/Theme.ImagePicker.Button.Icon.Circular"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="end"
                        android:layout_marginEnd="16dp"
                        app:icon="@drawable/baseline_close_24"
                        app:iconTint="@color/colorIcon" />
            
                </com.google.android.material.appbar.MaterialToolbar>
            
            </com.google.android.material.appbar.AppBarLayout>
        """.trimIndent()
    }
}
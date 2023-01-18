package id.andgen.andgen.actions.image_picker.codes.src.res.values

object ThemesXml {

    const val FILE_NAME = "themes"
    const val EXTENSION = "xml"

    fun getCode(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <style name="Theme.ImagePicker" parent="Theme.MaterialComponents.Light.NoActionBar" />
            
                <style name="TextView.ToolbarTitle" parent="android:Widget.TextView">
                    <item name="android:textSize">18sp</item>
                    <item name="android:textStyle">bold</item>
                    <item name="android:textColor">@color/colorTextPrimary</item>
                </style>
            
                <style name="Theme.ImagePicker.Button.Circular" parent="Widget.MaterialComponents.Button">
                    <item name="android:layout_width">40dp</item>
                    <item name="android:layout_height">40dp</item>
                    <item name="strokeWidth">0dp</item>
                    <item name="cornerRadius">20dp</item>
                    <item name="iconTint">@color/colorIcon</item>
                    <item name="backgroundTint">@color/colorBackground</item>
                    <item name="android:insetBottom">0dp</item>
                    <item name="android:insetTop">0dp</item>
                    <item name="iconGravity">textStart</item>
                    <item name="iconPadding">02dp</item>
                    <item name="elevation">8dp</item>
                </style>
            
                <style name="Theme.ImagePicker.Button.Nude" parent="Widget.MaterialComponents.Button.TextButton">
                    <item name="android:layout_width">match_parent</item>
                    <item name="android:layout_height">43dp</item>
                    <item name="android:insetTop">0dp</item>
                    <item name="android:insetBottom">0dp</item>
                    <item name="android:letterSpacing">0</item>
                    <item name="android:textAllCaps">false</item>
                    <item name="android:textStyle">bold</item>
                    <item name="cornerRadius">7dp</item>
                </style>
            
                <style name="Theme.ImagePicker.Button.Icon.Circular" parent="Widget.Material3.Button.IconButton">
                    <item name="android:layout_width">wrap_content</item>
                    <item name="android:layout_height">wrap_content</item>
                    <item name="iconSize">24dp</item>
                </style>
            
                <style name="Theme.ImagePicker.CardView.BottomSheetIndicator" parent="Widget.MaterialComponents.CardView">
                    <item name="android:layout_width">32dp</item>
                    <item name="android:layout_height">4dp</item>
                    <item name="cardBackgroundColor">@color/colorStroke</item>
                    <item name="cardCornerRadius">2dp</item>
                    <item name="cardElevation">0dp</item>
                </style>
            </resources>
        """.trimIndent()
    }
}
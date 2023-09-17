package academy.bangkit.githubuser.ui.setting

import academy.bangkit.githubuser.R
import academy.bangkit.githubuser.ui.extension.observeThemeSetting
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        observeThemeSetting(switchTheme)
    }
}
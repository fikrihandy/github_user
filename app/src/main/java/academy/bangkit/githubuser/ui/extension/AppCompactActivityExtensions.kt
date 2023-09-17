package academy.bangkit.githubuser.ui.extension

import academy.bangkit.githubuser.ui.setting.SettingPreferences
import academy.bangkit.githubuser.ui.setting.SettingViewModel
import academy.bangkit.githubuser.ui.setting.ViewModelFactory
import academy.bangkit.githubuser.ui.setting.dataStore
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

fun AppCompatActivity.observeThemeSetting(switchTheme: SwitchMaterial) {
    val pref = SettingPreferences.getInstance(application.dataStore)
    val settingViewModel =
        ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

    settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        switchTheme.isChecked = isDarkModeActive
    }

    switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
        settingViewModel.saveThemeSetting(isChecked)
    }
}
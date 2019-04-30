package com.example.semoto.meditation

data class UserSettings(
    var levelId: Int,
    var levelName: String,
    var themeId: Int,
    var themeName: String,
    var themeResId: Int,
    var themeSoundId: Int,
    var time: Int) {

    companion object {
        const val PREF_USERSETTINGS_NAME = "com.example.semoto.meditation.user_settings"
    }
}

enum class UserSettingsPrefKey{
    LEVEL_ID,
    LEVEL_NAME_STR_ID,
    THEME_ID,
    THEME_NAME_STR_ID,
    THEME_RES_ID,
    THEME_SOUND_ID,
    TIME
}
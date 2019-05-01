package com.example.semoto.meditation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.semoto.meditation.data.ThemeData
import com.example.semoto.meditation.model.UserSettings
import com.example.semoto.meditation.model.UserSettingsRepository
import com.example.semoto.meditation.util.PlayStatus

class MainViewModel: ViewModel() {

    var msgUpperSmall = MutableLiveData<String>()
    var msgLowerLarge = MutableLiveData<String>()
    var themePicFileResId = MutableLiveData<Int>()
    var txtTheme = MutableLiveData<String>()
    var txtLevel = MutableLiveData<String>()

    var remainedTimeSeconds = MutableLiveData<Int>()
    var displayTimeSeconds = MutableLiveData<String>()

    var playStatus = MutableLiveData<Int>()

    private val userSettingsRepository = UserSettingsRepository()
    private lateinit var userSettings: UserSettings

    fun initParameters() {

        userSettings = userSettingsRepository.loadUserSettings()

        msgUpperSmall.value = ""
        msgLowerLarge.value = ""
        themePicFileResId.value = userSettings.themeResId
        txtTheme.value = userSettings.themeName
        txtLevel.value = userSettings.levelName
        remainedTimeSeconds.value = userSettings.time * 60
        displayTimeSeconds.value = changeTimeFormat(userSettings.time * 60)
        playStatus.value = PlayStatus.BEFORE_START
    }

    private fun changeTimeFormat(timeSeconds: Int): String? {

        val minutes = timeSeconds / 60
        val minutesString = if (minutes < 10) "0" + minutes.toString() else minutes.toString()

        val seconds = timeSeconds - minutes * 60
        val secondsString = if (seconds < 10) "0" + seconds.toString() else seconds.toString()

        return "$minutesString : $secondsString"
    }

    fun setLevel(selectedItemId: Int) {

        txtLevel.value = userSettingsRepository.setLevel(selectedItemId)
    }

    fun setTime(selectedItemId: Int) {

        remainedTimeSeconds.value = userSettingsRepository.setTime(selectedItemId) * 60
        displayTimeSeconds.value = changeTimeFormat(userSettingsRepository.setTime(selectedItemId) * 60)
    }

    fun setTheme(themeData: ThemeData) {

        userSettingsRepository.setTheme(themeData)
        txtTheme.value = userSettingsRepository.loadUserSettings().themeName
        themePicFileResId.value = userSettingsRepository.loadUserSettings().themeResId
    }

}
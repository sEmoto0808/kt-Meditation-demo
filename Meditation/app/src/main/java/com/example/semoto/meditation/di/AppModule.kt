package com.example.semoto.meditation.di

import android.app.Application
import com.example.semoto.meditation.model.UserSettingsRepository
import com.example.semoto.meditation.service.MusicServiceHelper
import com.example.semoto.meditation.util.NotificationHelper
import com.example.semoto.meditation.view.dialog.LevelSelectDialog
import com.example.semoto.meditation.view.dialog.ThemeSelectDialog
import com.example.semoto.meditation.view.dialog.TimeSelectDialog
import com.example.semoto.meditation.view.main.MainFragment
import com.example.semoto.meditation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    factory { MainFragment() }
    factory { LevelSelectDialog() }
    factory { ThemeSelectDialog() }
    factory { TimeSelectDialog() }
    factory { MusicServiceHelper(get()) }
    factory { NotificationHelper(get()) }
    factory { UserSettingsRepository() }

    viewModel { MainViewModel(androidContext() as Application) }
}
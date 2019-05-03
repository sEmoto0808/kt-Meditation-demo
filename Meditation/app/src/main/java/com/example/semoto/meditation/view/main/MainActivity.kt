package com.example.semoto.meditation.view.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.semoto.meditation.R
import com.example.semoto.meditation.service.MusicServiceHelper
import com.example.semoto.meditation.util.FragmentTag
import com.example.semoto.meditation.util.NotificationHelper
import com.example.semoto.meditation.util.PlayStatus
import com.example.semoto.meditation.view.dialog.LevelSelectDialog
import com.example.semoto.meditation.view.dialog.ThemeSelectDialog
import com.example.semoto.meditation.view.dialog.TimeSelectDialog
import com.example.semoto.meditation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    //    private var musicServiceHelper: MusicServiceHelper? = null
    private val musicServiceHelper: MusicServiceHelper by inject()

    //    private var notificationHelper: NotificationHelper? = null
    private val notificationHelper: NotificationHelper by inject()

    private val mainFragment: MainFragment by inject()

    private val levelSelectDialog: LevelSelectDialog by inject()
    private val themeSelectDialog: ThemeSelectDialog by inject()
    private val timeSelectDialog: TimeSelectDialog by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.screen_container,
                    mainFragment
                )
                .commit()
        }

        observeViewModel()

        btmNavi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_select_level -> {
                    levelSelectDialog.show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                    themeSelectDialog.show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_time -> {
                    timeSelectDialog.show(supportFragmentManager, FragmentTag.TIME_SELECT.name)
                    true
                }
                else -> {
                    false
                }
            }
        }

        musicServiceHelper.bindService()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        musicServiceHelper.stopBgm()
        finish()
    }

    override fun onResume() {
        super.onResume()

        notificationHelper.cancelNotification()
    }

    override fun onPause() {
        super.onPause()

        notificationHelper.startNotification()
    }

    override fun onDestroy() {
        super.onDestroy()

        notificationHelper.cancelNotification()
    }

    private fun observeViewModel() {

        viewModel.playStatus.observe(this, Observer { status ->
            when (status) {
                PlayStatus.BEFORE_START -> {
                    btmNavi.visibility = View.VISIBLE
                }
                PlayStatus.ON_START -> {
                    btmNavi.visibility = View.INVISIBLE
                }
                PlayStatus.RUNNING -> {
                    btmNavi.visibility = View.INVISIBLE
                    musicServiceHelper.startBgm()
                }
                PlayStatus.PAUSE -> {
                    btmNavi.visibility = View.INVISIBLE
                    musicServiceHelper.stopBgm()
                }
                PlayStatus.END -> {
                    musicServiceHelper.stopBgm()
                    musicServiceHelper.ringFinalGong()
                }
            }
        })

        viewModel.volume.observe(this, Observer { volume ->
            musicServiceHelper.setVolume(volume!!)
        })
    }
}

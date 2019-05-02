package com.example.semoto.meditation.view.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.semoto.meditation.R
import com.example.semoto.meditation.service.MusicService
import com.example.semoto.meditation.service.MusicServiceHelper
import com.example.semoto.meditation.util.FragmentTag
import com.example.semoto.meditation.util.PlayStatus
import com.example.semoto.meditation.view.dialog.LevelSelectDialog
import com.example.semoto.meditation.view.dialog.ThemeSelectDialog
import com.example.semoto.meditation.view.dialog.TimeSelectDialog
import com.example.semoto.meditation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var musicServiceHelper: MusicServiceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.screen_container,
                    MainFragment()
                )
                .commit()
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()

        btmNavi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_select_level -> {
                    LevelSelectDialog().show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                    ThemeSelectDialog().show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_time -> {
                    TimeSelectDialog().show(supportFragmentManager, FragmentTag.TIME_SELECT.name)
                    true
                }
                else -> { false }
            }
        }

        musicServiceHelper = MusicServiceHelper(this)
        musicServiceHelper?.bindService()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        musicServiceHelper?.stopBgm()
        finish()
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
                    musicServiceHelper?.startBgm()
                }
                PlayStatus.PAUSE -> {
                    btmNavi.visibility = View.INVISIBLE
                    musicServiceHelper?.stopBgm()
                }
                PlayStatus.END -> {
                    musicServiceHelper?.stopBgm()
                    musicServiceHelper?.ringFinalGong()
                }
            }
        })
    }
}

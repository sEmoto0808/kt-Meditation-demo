package com.example.semoto.meditation.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {

    private val binder: IBinder = MusicBinder()

    inner class MusicBinder: Binder() {

        fun getService(): MusicService {
            return this@MusicService
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun startBgm() {}

    fun stopBgm() {}

    fun setVolume() {}

    fun ringFinalGong() {}
}

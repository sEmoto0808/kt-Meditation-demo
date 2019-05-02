package com.example.semoto.meditation.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class MusicServiceHelper(val context: Context) {

    private var musicService: MusicService? = null

    fun bindService() {

        val serviceConnection = object : ServiceConnection {


            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

                val binder = service as MusicService.MusicBinder
                musicService = binder.getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {


            }
        }

        val intent = Intent(context, MusicService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun startBgm() {
        musicService?.startBgm()
    }

    fun stopBgm() {
        musicService?.stopBgm()
    }

    fun setVolume() {}

    fun ringFinalGong() {
        musicService?.ringFinalGong()
    }
}
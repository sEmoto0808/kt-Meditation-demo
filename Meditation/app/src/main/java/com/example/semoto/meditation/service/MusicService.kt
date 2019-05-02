package com.example.semoto.meditation.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Binder
import android.os.IBinder
import com.example.semoto.meditation.MyApplication
import com.example.semoto.meditation.R
import com.example.semoto.meditation.model.UserSettingsRepository
import com.example.semoto.meditation.util.NO_BGM
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class MusicService : Service() {

    private val binder: IBinder = MusicBinder()

    inner class MusicBinder : Binder() {

        fun getService(): MusicService {
            return this@MusicService
        }
    }

    private var bellPlayer: SimpleExoPlayer? = null
    private var bgmPlayer: SimpleExoPlayer? = null

    private var rawResourceDataSourceBell: RawResourceDataSource? = null
    private var rawResourceDataSourceBgm: RawResourceDataSource? = null

    private var soundPool: SoundPool? = null
    private var soundIdGong: Int = 0

    private lateinit var audioManager: AudioManager
    private var maxVolume = 0

    override fun onBind(intent: Intent): IBinder {

        bellPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        bgmPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())

        rawResourceDataSourceBell = RawResourceDataSource(this)
        rawResourceDataSourceBgm = RawResourceDataSource(this)

        initSoundPool()

        initAudioManager()

        return binder
    }

    private fun initAudioManager() {

        audioManager = MyApplication.appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    private fun initSoundPool() {

        soundPool = SoundPool.Builder().setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
            .setMaxStreams(1)
            .build()

        soundIdGong = soundPool?.load(this, R.raw.gong_meiso_end, 1)!!
    }

    override fun onUnbind(intent: Intent?): Boolean {
        soundPool?.release()
        releaseExoPlayer()
        return super.onUnbind(intent)
    }

    fun startBgm() {

        val userSettingsRepository = UserSettingsRepository()
        val bellSoundId = when (userSettingsRepository.loadUserSettings().levelId) {
            0 -> R.raw.bells_easy
            1 -> R.raw.bells_normal
            2 -> R.raw.bells_mid
            3 -> R.raw.bells_advanced
            else -> 0
        }
        val themeSoundId = userSettingsRepository.loadUserSettings().themeSoundId

        bellPlayer?.let { startSound(it, bellSoundId, rawResourceDataSourceBell!!) }
        if (themeSoundId != NO_BGM) bgmPlayer?.let { startSound(it, themeSoundId, rawResourceDataSourceBgm!!) }
    }

    private fun startSound(exoPlayer: SimpleExoPlayer, soundId: Int, rawResourceDataSource: RawResourceDataSource) {

        val dataSourceUri = RawResourceDataSource.buildRawResourceUri(soundId)
        val dataSpec = DataSpec(dataSourceUri)
        rawResourceDataSource.open(dataSpec)
        val dataSourceFactory = DataSource.Factory { return@Factory rawResourceDataSource }
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(Mp3Extractor.FACTORY)
            .createMediaSource(dataSourceUri)
        exoPlayer.apply {
            prepare(mediaSource)
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
        }
    }

    fun stopBgm() {
        bellPlayer?.stop()
        bgmPlayer?.stop()
    }

    fun setVolume(volume: Int) {
        val adjustedVolume = (maxVolume.toDouble() * volume.toDouble() / 100.toDouble()).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, adjustedVolume, 0)
    }

    fun ringFinalGong() {
        soundPool?.play(soundIdGong, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    private fun releaseExoPlayer() {
        bellPlayer?.release()
        bgmPlayer?.release()
        rawResourceDataSourceBell?.close()
        rawResourceDataSourceBgm?.close()
    }
}

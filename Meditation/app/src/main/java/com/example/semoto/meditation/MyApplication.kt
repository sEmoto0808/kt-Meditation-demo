package com.example.semoto.meditation

import android.app.Application
import android.content.Context
import com.example.semoto.meditation.data.ThemeData
import com.example.semoto.meditation.util.NO_BGM
import com.example.semoto.meditation.util.ThemeId

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = this
        themeList = setThemeData()
    }

    private fun setThemeData(): ArrayList<ThemeData> {

        return arrayListOf(
            ThemeData(
                ThemeId.SILENT, R.string.theme_silent, R.drawable.pic_nobgm, R.drawable.pic_nobgm_sq,
                NO_BGM
            ),
            ThemeData(
                ThemeId.CAVE,
                R.string.theme_cave,
                R.drawable.pic_cave,
                R.drawable.pic_cave_sq,
                R.raw.bgm_cave
            ),
            ThemeData(
                ThemeId.MT_SPRING,
                R.string.theme_spring_mt,
                R.drawable.pic_spring_mountain,
                R.drawable.pic_spring_mountain_sq,
                R.raw.bgm_spring_mountain
            ),
            ThemeData(
                ThemeId.MT_SUMMER,
                R.string.theme_summer_mt,
                R.drawable.pic_summer_mountain,
                R.drawable.pic_summer_mountain_sq,
                R.raw.bgm_summer_mountain
            ),
            ThemeData(
                ThemeId.RICE_FIELD,
                R.string.theme_autumn_rice_field,
                R.drawable.pic_autumn_rice_field,
                R.drawable.pic_autumn_rice_field_sq,
                R.raw.bgm_autumn_rice_field
            ),
            ThemeData(
                ThemeId.STREAM,
                R.string.theme_stream,
                R.drawable.pic_stream,
                R.drawable.pic_stream_sq,
                R.raw.bgm_pond
            ),
            ThemeData(
                ThemeId.WIND_BELLS,
                R.string.theme_windbells,
                R.drawable.pic_windbell,
                R.drawable.pic_windbell_sq,
                R.raw.bgm_wind_bells
            ),
            ThemeData(
                ThemeId.FIRE_WORKS,
                R.string.theme_fireworks,
                R.drawable.pic_firework,
                R.drawable.pic_firework_sq,
                R.raw.bgm_fire
            )
        )
    }

    companion object {
        lateinit var appContext: Context
        lateinit var themeList: ArrayList<ThemeData>
    }
}
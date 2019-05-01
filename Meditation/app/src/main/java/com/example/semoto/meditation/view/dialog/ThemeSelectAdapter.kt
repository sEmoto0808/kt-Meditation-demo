package com.example.semoto.meditation.view.dialog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.semoto.meditation.MyApplication
import com.example.semoto.meditation.R
import com.example.semoto.meditation.data.ThemeData
import com.example.semoto.meditation.viewmodel.MainViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.theme_detail_card.*

class ThemeSelectAdapter(private val themeList: ArrayList<ThemeData>,
                         private val viewModel: MainViewModel) : RecyclerView.Adapter<ThemeSelectAdapter.ViewHolder>() {

    private val appContext = MyApplication.appContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.theme_detail_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(VH: ViewHolder, position: Int) {

        VH.apply {
            val themeData = themeList[position]
            textTheme.text = appContext.resources.getString(themeData.themeNameResId)
            Glide.with(appContext).load(themeData.themeSqPicResId).into(imageTheme)
            containerView?.setOnClickListener {
                viewModel.setTheme(themeData)
            }
        }
    }

    override fun getItemCount(): Int {
        return themeList.size
    }


    inner class ViewHolder(override val containerView: View?): RecyclerView.ViewHolder(containerView!!), LayoutContainer {}


}

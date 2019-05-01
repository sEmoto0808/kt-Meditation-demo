package com.example.semoto.meditation.view.dialog

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.semoto.meditation.MyApplication
import com.example.semoto.meditation.R
import com.example.semoto.meditation.viewmodel.MainViewModel

class ThemeSelectDialog: DialogFragment() {

    private val appContext = MyApplication.appContext
    private val themeList = MyApplication.themeList
    private lateinit var viewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val recyclerView = RecyclerView(appContext)
        with(recyclerView) {
            layoutManager = GridLayoutManager(appContext, 2)
            adapter = ThemeSelectAdapter(themeList, viewModel)
        }

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_theme)
            setView(recyclerView)
        }
            .create()

        viewModel.txtTheme.observe(activity!!, Observer {
            dialog.dismiss()
        })

        return dialog
    }
}
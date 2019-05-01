package com.example.semoto.meditation.view.dialog

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.semoto.meditation.R
import com.example.semoto.meditation.viewmodel.MainViewModel

class LevelSelectDialog: DialogFragment() {

    var selectedItemId = 0

    lateinit var viewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_level)
            setSingleChoiceItems(R.array.level_list, selectedItemId) { dialog, which ->
                selectedItemId = which
                viewModel.setLevel(selectedItemId)
                dialog.dismiss()
            }
        }
            .create()

        return  dialog
    }
}
package edu.washington.snalegave.quizdroid

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.DialogInterface
import android.content.Intent




class AirplaneFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {

        return AlertDialog.Builder(activity)
                .setTitle("Turn off airplane mode")
                .setPositiveButton("OK") {
                    dialog, which ->
                    startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                }
                .setNegativeButton("NO"){
                    dialog, which ->
                }
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                }

    }

}

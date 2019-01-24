package com.example.story.githubinfo.module

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.story.githubinfo.R

open class BaseActivity : AppCompatActivity() {
    private var loadingDialogList: ArrayList<AlertDialog?> = ArrayList()
    protected lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this;
    }

    fun showLoadingDialog() {
        loadingDialogList.add(
                AlertDialog.Builder(context)
                        .setView(View.inflate(applicationContext, R.layout.dialog_progress,null))
                        .setCancelable(false)
                        .create().apply {
                            window.setBackgroundDrawableResource(android.R.color.transparent)
                            window.setDimAmount(0.25F)
                            show()
                        }
        )
    }

    fun dismissLoadingDialog() {
        loadingDialogList.forEach { it?.dismiss() }
        loadingDialogList.clear()
    }
}
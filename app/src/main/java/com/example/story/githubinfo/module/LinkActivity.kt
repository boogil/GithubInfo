package com.example.story.githubinfo.module

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.story.githubinfo.MainActivity
import com.example.story.githubinfo.R

class LinkActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var gotIntent = intent
        var userName = gotIntent.data.schemeSpecificPart.split("repos/")[1]
        var sendIntent = Intent(this, MainActivity::class.java)
        sendIntent.putExtra("userName", userName)
        startActivity(sendIntent)
    }
}
package com.example.story.githubinfo

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.appknot.mygoodfeel.module.RetrofitBuilder
import com.example.story.githubinfo.module.GitDataAdapter
import com.example.story.githubinfo.module.RetrofitApi
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var userName = intent.getStringExtra("userName")?.let { it } ?: kotlin.run { "boogil" }
        var gitDataList: ArrayList<LinkedTreeMap<String, Any>> = ArrayList()
        RetrofitBuilder().apply {
            call = create(RetrofitApi.GithubApiInfo::class.java).addParams(userName)
            onSuccess {
                gitDataList.add(it as LinkedTreeMap<String, Any>)
                RetrofitBuilder().apply {
                    call = create(RetrofitApi.GithubApiRepo::class.java).addParams(userName)
                    onSuccess {
                        var tmp = it as ArrayList<LinkedTreeMap<String, Any>>
                        tmp.forEach {
                            gitDataList.add(it)
                        }
                        RV_MAIN.addItemDecoration(DividerItemDecoration(
                                context, DividerItemDecoration.VERTICAL))
                        RV_MAIN.layoutManager = LinearLayoutManager(context)
                        RV_MAIN.adapter = GitDataAdapter(context, gitDataList)
                    }
                    onError { _, msg -> }
                    onFailure {}
                    executeWithProgress(context)
                }
            }
            onError { _, msg -> }
            onFailure {}
            executeWithProgress(context)
        }
    }
}

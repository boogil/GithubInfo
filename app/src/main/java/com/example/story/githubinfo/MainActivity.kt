package com.example.story.githubinfo

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.appknot.mygoodfeel.module.RetrofitBuilder
import com.example.story.githubinfo.module.GitDataAdapter
import com.example.story.githubinfo.module.RetrofitApi
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var userName = intent.getStringExtra("userName")?.let { it } ?: kotlin.run { "boogil" }
        var gitDataList: ArrayList<LinkedTreeMap<String, Any>> = ArrayList()
        RetrofitBuilder().apply {
            call = create(RetrofitApi.GithubApiInfo::class.java).addParams(userName)
            onSuccess {
                var headerData = it as LinkedTreeMap<String, Any>
                RetrofitBuilder().apply {
                    call = create(RetrofitApi.GithubApiRepo::class.java).addParams(userName)
                    onSuccess {
                        var tmp = it as ArrayList<LinkedTreeMap<String, Any>>
                        tmp.forEach {
                            gitDataList.add(it)
                        }
                        // stargazers_count 내림차순 정렬
                        var sortedList = gitDataList.sortedWith(compareBy({
                            it.get("stargazers_count")?.toString()?.toDouble()})).reversed()
                        var sortedArrayList = ArrayList(sortedList)
                        sortedArrayList.add(0, headerData)
                        RV_MAIN.addItemDecoration(DividerItemDecoration(
                                context, DividerItemDecoration.VERTICAL))
                        RV_MAIN.layoutManager = LinearLayoutManager(context)
                        RV_MAIN.adapter = GitDataAdapter(context, sortedArrayList)
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

//    class IntegerComparator : Comparator<List<LinkedTreeMap<String, Any>>> {
//        override fun compare(o1: List<LinkedTreeMap<String, Any>>?, o2: List<LinkedTreeMap<String, Any>>?): Int {
//            var count1 = Integer.parseInt(o1?.get("stargazers_count").toString())
//            var count2 = Integer.parseInt(o1?.get("stargazers_count").toString())
//            return count2.compareTo(count1)
//        }
//
//        override fun compare(o1: LinkedTreeMap<String, Any>?, o2: LinkedTreeMap<String, Any>?): Int {
//
//        }
//    }
}

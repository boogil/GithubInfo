package com.example.story.githubinfo

import android.content.Context
import com.example.story.githubinfo.BaseActivity
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Retrofit 재사용
 */
class RetrofitBuilder {
    fun <T> create(apiInterface: Class<T>): T = retrofit.create(apiInterface)
    fun <T> create(apiInterface: Class<T>, baseUrl: String): T =
            buildRetrofit(baseUrl).create(apiInterface)

    lateinit var call: Call<Any>
    private var context: Context? = null
    private var progressVisibility = false
    private var successListener: ((Any) -> Unit)? = null
    private var errorListener: ((String, String) -> Unit)? = null
    private var failureListener: (() -> Unit)? = null

    /**
     * 요청 수행
     */
    fun execute() {
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                hideProgress()
                response.body()?.run {
                    response;
//                    when (this.code) {
//                        CODE_SUCCESS -> {successListener?.invoke(this.data) }
//                        else -> {errorListener?.invoke(this.code, this.msg.ko) }
//                    }
                    successListener?.invoke(this)

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                hideProgress()
                failureListener?.invoke()
            }
        })
    }

    /**
     *  요청 수행 with ProgressBar
     */
    fun executeWithProgress(context: Context?) {
        if (context is BaseActivity) {
            this.context = context
            context.showLoadingDialog()
            progressVisibility = true
        }
        execute()
    }



    /**
     * JSON 정보 담고 있는 맵을 자바 객체로 변환
     * @param data JSON 정보를 담고 있는 맵 객체
     * @param modelType 결과를 담을 자바 객체 클래스 타입
     * @return 변환 완료된 자바 객체 클래스 타입의 인스턴스
     */
    fun <T> parse(data: LinkedTreeMap<*, *>, modelType: Class<T>): T {
        val jsonStr = Gson().toJson(data)
        return Gson().fromJson(jsonStr, modelType)
    }

    fun onSuccess(body: (data: Any) -> Unit): RetrofitBuilder {
        successListener = body
        return this
    }

    fun onError(body: (code: String, msg: String) -> Unit): RetrofitBuilder {
        errorListener = body
        return this
    }

    fun onFailure(body: () -> Unit): RetrofitBuilder {
        failureListener = body
        return this
    }

    private fun hideProgress() {
        if (progressVisibility && context is BaseActivity) {
            (context as BaseActivity).dismissLoadingDialog()
            progressVisibility = false
        }
    }

    companion object {
        const val CODE_SUCCESS = "0"

        const val apiPath = "https://api.github.com/users/"
        private var retrofit = buildRetrofit(apiPath)

        fun buildRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
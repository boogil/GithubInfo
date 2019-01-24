package com.example.story.githubinfo.module


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


class RetrofitApi {
    interface GithubApiInfo {
        @GET("{name}")
        fun addParams(@Path("name") id: String?) : Call<Any>
    }

    interface GithubApiRepo {
        @GET("{name}/repos??sort=stargazers_count")
        fun addParams(@Path("name") id: String?) : Call<Any>
    }
}
package com.parulrabh.dbs.network

import com.parulrabh.dbs.model.ArticlesModel
import retrofit2.Call
import retrofit2.http.GET

interface ArticlesService {


    @GET(NetworkConstants.ARTICLES)
    fun getArticles(): Call<List<ArticlesModel>>
}
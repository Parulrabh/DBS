package com.parulrabh.dbs.network

import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.NetworkConstants.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetArticlesSync {

    sealed class ArticleResponse<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Success<T>(data: T?) : ArticleResponse<T>(data)
        class Error<T>(data: T? = null, message: String) : ArticleResponse<T>(data, message)
    }

    fun getArticles(passResponseFunc : (ArticleResponse<List<ArticlesModel>>) -> Unit){
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ArticlesService::class.java)
        val call = service.getArticles()
        call.enqueue(object : Callback<List<ArticlesModel>> {
            override fun onResponse(
                call: Call<List<ArticlesModel>>,
                response: Response<List<ArticlesModel>>
            ) {
                if (response.code() == 200) {
                    passResponseFunc(ArticleResponse.Success(response.body()))
                }
                else{
                    passResponseFunc(ArticleResponse.Error(message = response.message()))
                }
            }

            override fun onFailure(call: Call<List<ArticlesModel>>, t: Throwable) {
                passResponseFunc(ArticleResponse.Error(message = t.message ?: t.localizedMessage))
            }
        })
    }
}
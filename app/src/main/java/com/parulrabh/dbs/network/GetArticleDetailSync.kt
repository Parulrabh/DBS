package com.parulrabh.dbs.network

import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.network.NetworkConstants.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class GetArticleDetailSync {
    fun getArticleDetail(articleID : Int , passResponseFunc : (ArticleAPIResponse<ArticleDetailModel>) -> Unit){
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ArticleDetailService::class.java)
        val call = service.getArticleDetail(articleID)
        call.enqueue(object : Callback<ArticleDetailModel> {
            override fun onResponse(
                call: Call<ArticleDetailModel>,
                response: Response<ArticleDetailModel>
            ) {
                if (response.code() == 200) {
                    passResponseFunc(ArticleAPIResponse.Success(response.body()))
                }
                else{
                    passResponseFunc(ArticleAPIResponse.Error(message = response.message()))
                }
            }

            override fun onFailure(call: Call<ArticleDetailModel>, t: Throwable) {
                passResponseFunc(ArticleAPIResponse.Error(message = t.message ?: t.localizedMessage))
            }
        })
    }
}
interface ArticleDetailService {
    @GET(NetworkConstants.ARTICLES+"/{articleID}")
    fun getArticleDetail(@Path("articleID") articleID : Int) : Call<ArticleDetailModel>

}
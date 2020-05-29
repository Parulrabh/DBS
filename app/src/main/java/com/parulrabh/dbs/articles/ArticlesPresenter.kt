package com.parulrabh.dbs.articles

import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.ArticleAPIResponse
import com.parulrabh.dbs.network.GetArticlesSync
import java.text.SimpleDateFormat
import java.util.*

class ArticlesPresenter(var view : ArticlesContract.View, var articlesDataHandler : GetArticlesSync) : ArticlesContract.Presenter {
    override fun fetchArticles() {
        //call the API to get the Articles
        view.displayLoader()
        articlesDataHandler.getArticles { response -> fetchArticleResponse(response) }
    }

    override fun getDateDisplayFormat(date: Long?): String {
        var updatedDate = date?.let { Date(it) }
        var df = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
        return df.format(updatedDate?: Date())
    }

    override fun callArticleDetails(articleData: ArticlesModel?) {
        view.onArticleSelected(articleData)
    }

    private fun fetchArticleResponse(response : ArticleAPIResponse<List<ArticlesModel>>) {
        when(response){
            is ArticleAPIResponse.Success ->{
                view.hideLoader()
                view.displayArticles(response)
            }
            is ArticleAPIResponse.Error ->{
                view.hideLoader()
                view.displayErrorDialog(response.message)
            }
        }

    }
}
package com.parulrabh.dbs.articles

import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.GetArticlesSync

interface ArticlesContract {

    interface View{
        fun displayLoader()
        fun hideLoader()
        fun displayArticles(response : GetArticlesSync.ArticleResponse<List<ArticlesModel>>)
        fun onArticleSelected(id : Int?)
        fun displayErrorDialog(message : String?)

    }

    interface Presenter{
        fun fetchArticles()
        fun getDateDisplayFormat(date : Long?) : String
        fun callArticleDetails(id : Int?)
    }
}
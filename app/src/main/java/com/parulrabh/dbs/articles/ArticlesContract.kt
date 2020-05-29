package com.parulrabh.dbs.articles

import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.ArticleAPIResponse

interface ArticlesContract {

    interface View{
        fun displayLoader()
        fun hideLoader()
        fun displayArticles(response : ArticleAPIResponse<List<ArticlesModel>>)
        fun onArticleSelected(articleData : ArticlesModel?)
        fun displayErrorDialog(message : String?)

    }

    interface Presenter{
        fun fetchArticles()
        fun getDateDisplayFormat(date : Long?) : String
        fun callArticleDetails(articleData: ArticlesModel?)
    }
}
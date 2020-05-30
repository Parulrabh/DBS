package com.parulrabh.dbs.articles

import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.mvp.BaseView
import com.parulrabh.dbs.network.ArticleAPIResponse

interface ArticlesContract {

    interface View : BaseView {
        fun displayArticles(response : List<ArticlesModel>?)
        fun onArticleSelected(articleData : ArticlesModel?)
    }

    interface Presenter{
        fun fetchArticles()
        fun getDateDisplayFormat(date : Long?) : String
        fun callArticleDetails(articleData: ArticlesModel?)
    }
}
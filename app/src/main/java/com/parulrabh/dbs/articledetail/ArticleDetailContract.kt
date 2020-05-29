package com.parulrabh.dbs.articledetail

import com.parulrabh.dbs.model.ArticleDetailModel

interface ArticleDetailContract {
    interface View{
        fun displayLoader()
        fun hideLoader()
        fun displayArticleDetail(articleData : ArticleDetailModel?)
        fun editButtonClicked()
        fun saveButtonClicked()
        fun cancelButtonClicked()
        fun setArticleDetailData()
        fun displayErrorDialog(message : String?)
    }
    interface Presenter{
        fun fetchArticleDetail(articleId : Int)
    }
}
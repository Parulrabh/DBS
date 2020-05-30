package com.parulrabh.dbs.articledetail

import android.content.SharedPreferences
import com.parulrabh.dbs.cache.CacheRepository
import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.mvp.BaseView

interface ArticleDetailContract {
    interface View : BaseView {
        fun displayArticleDetail(articleData : ArticleDetailModel?)
        fun editButtonClicked()
        fun saveButtonClicked()
        fun cancelButtonClicked()
        fun setArticleDetailData()
    }
    interface Presenter{
        fun fetchArticleDetail(articleId : Int)
        fun setCacheRepository(cacheRepository: CacheRepository)
        fun saveArticleToCacheRepo(articleData : ArticleDetailModel)
        fun getArticleFromCacheRepo(articleID : Int) : String?
    }
}
package com.parulrabh.dbs.articledetail

import android.content.SharedPreferences
import com.parulrabh.dbs.cache.CacheRepository
import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.network.ArticleAPIResponse
import com.parulrabh.dbs.network.GetArticleDetailSync

class ArticleDetailPresenter(
    var view: ArticleDetailContract.View,
    var articleDetailDataHandler: GetArticleDetailSync
) : ArticleDetailContract.Presenter {
    lateinit var cacheRepo : CacheRepository
    override fun fetchArticleDetail(articleId: Int) {
        var articleText = getArticleFromCacheRepo(articleId)
        if(articleText != null){
            var articleDetailModel = ArticleDetailModel(articleId, articleText)
            view.displayArticleDetail(articleDetailModel)
        }
        else{
            view.displayLoader()
            articleDetailDataHandler.getArticleDetail(articleId) { response ->
                fetchArticleResponse(
                    response
                )
            }
        }
    }

    override fun setCacheRepository(cacheRepository: CacheRepository) {
        cacheRepo = cacheRepository
    }

    override fun saveArticleToCacheRepo(articleData: ArticleDetailModel) {
        if(!articleData.text.isBlank())
            cacheRepo.saveToPreference(articleData)
    }

    override fun getArticleFromCacheRepo(articleID: Int): String? {
        return cacheRepo.getFromPreference(articleID)
    }

    private fun fetchArticleResponse(response: ArticleAPIResponse<ArticleDetailModel>) {
        when (response) {
            is ArticleAPIResponse.Success -> {
                view.hideLoader()
                view.displayArticleDetail(response.data)
            }
            is ArticleAPIResponse.Error -> {
                view.hideLoader()
                view.displayErrorDialog(response.message)
            }
        }

    }
}
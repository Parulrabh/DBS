package com.parulrabh.dbs.articledetail

import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.network.ArticleAPIResponse
import com.parulrabh.dbs.network.GetArticleDetailSync

class ArticleDetailPresenter(
    var view: ArticleDetailContract.View,
    var articleDetailDataHandler: GetArticleDetailSync
) : ArticleDetailContract.Presenter {
    override fun fetchArticleDetail(articleId: Int) {
        view.displayLoader()
        articleDetailDataHandler.getArticleDetail(articleId) { response ->
            fetchArticleResponse(
                response
            )
        }
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
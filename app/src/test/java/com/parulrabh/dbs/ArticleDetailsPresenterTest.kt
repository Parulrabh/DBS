package com.parulrabh.dbs

import com.nhaarman.mockitokotlin2.any
import com.parulrabh.dbs.articledetail.ArticleDetailContract
import com.parulrabh.dbs.articledetail.ArticleDetailPresenter
import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.network.ArticleAPIResponse
import com.parulrabh.dbs.network.GetArticleDetailSync
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleDetailsPresenterTest {

    private lateinit var articleDetailPresenter : ArticleDetailPresenter
    @Mock
    lateinit var articlesDetailViewMock : ArticleDetailContract.View
    @Mock
    lateinit var getArticlesDetailMock : GetArticleDetailSync
    @Before
    fun setUp(){
        articleDetailPresenter = ArticleDetailPresenter(articlesDetailViewMock,getArticlesDetailMock)
    }

    //test when fetch article successful correct listener called
    @Test
    fun showArticle_fetchArticleDetailAPISuccessful_listenerNotifiedWithCorrectData(){
        getArticlesSuccess()
        articleDetailPresenter.fetchArticleDetail(1)
        Mockito.verify(articlesDetailViewMock).displayLoader()
        Mockito.verify(articlesDetailViewMock).hideLoader()
        Mockito.verify(articlesDetailViewMock).displayArticleDetail(any())
    }

    //test when fetch article failure error listener called
    @Test
    fun showArticle_fetchArticleDetailAPIFailure_listenerNotifiedWithError(){
        getArticlesFailure()
        articleDetailPresenter.fetchArticleDetail(1)
        Mockito.verify(articlesDetailViewMock).displayLoader()
        Mockito.verify(articlesDetailViewMock).hideLoader()
        Mockito.verify(articlesDetailViewMock).displayErrorDialog(any())
    }

    private fun getArticlesSuccess() {
        Mockito.`when`(getArticlesDetailMock.getArticleDetail(any(),any())).thenAnswer() {
            val argument = it.arguments[1]
            val response =
                argument as ((passResponseFunc: ArticleAPIResponse<ArticleDetailModel>) -> Unit)
            response.invoke(
                ArticleAPIResponse.Success(ArticleDetailModel(1,"this is the article 1 detail"))
            )

        }
    }

    private fun getArticlesFailure() {
        Mockito.`when`(getArticlesDetailMock.getArticleDetail(any(),any())).thenAnswer() {
            val argument = it.arguments[1]
            val response =
                argument as ((passResponseFunc: ArticleAPIResponse<ArticleDetailModel>) -> Unit)
            response.invoke(ArticleAPIResponse.Error(message = "Error"))
        }
    }
}

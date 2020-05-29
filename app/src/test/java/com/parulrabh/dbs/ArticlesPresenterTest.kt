package com.parulrabh.dbs


import com.nhaarman.mockitokotlin2.any
import com.parulrabh.dbs.articles.ArticlesContract
import com.parulrabh.dbs.articles.ArticlesPresenter
import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.ArticleAPIResponse
import com.parulrabh.dbs.network.GetArticlesSync
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ArticlesPresenterTest {

    private lateinit var articlePresenter : ArticlesPresenter
    @Mock
    lateinit var articlesViewMock : ArticlesContract.View
    @Mock
    lateinit var getArticlesMock : GetArticlesSync
    @Before
    fun setUp(){
        articlePresenter = ArticlesPresenter(articlesViewMock,getArticlesMock)
    }

    //test when fetch article successful correct listener called
    @Test
    fun showArticle_fetchArticleAPISuccessful_listenerNotifiedWithCorrectData(){
        getArticlesSuccess()
        articlePresenter.fetchArticles()
        verify(articlesViewMock).displayLoader()
        verify(articlesViewMock).hideLoader()
        verify(articlesViewMock).displayArticles(any())
    }

    //test when fetch article failure error listener called
    @Test
    fun showArticle_fetchArticleAPIFailure_listenerNotifiedWithError(){
        getArticlesFailure()
        articlePresenter.fetchArticles()
        verify(articlesViewMock).displayLoader()
        verify(articlesViewMock).hideLoader()
        verify(articlesViewMock).displayErrorDialog(any())
    }

    @Test
    fun date_dateinSeconds_wrongDateReturned(){
        var dateString = articlePresenter.getDateDisplayFormat(1590694418)
        assert(dateString != "2020-05-29")
    }

    @Test
    fun date_dateinMillisecond_correctDateReturned(){
        var dateString = articlePresenter.getDateDisplayFormat(1590694418000)
        assert(dateString == "2020-05-29")
    }

    private fun getArticlesSuccess() {
        Mockito.`when`(getArticlesMock.getArticles(any())).thenAnswer() {
            val argument = it.arguments[0]
            val response =
                argument as ((passResponseFunc: ArticleAPIResponse<List<ArticlesModel>>) -> Unit)
            response.invoke(
                ArticleAPIResponse.Success(
                    listOf(
                        ArticlesModel(1, "test 1", 1230098907, "short description", "get avatar")
                    )
                )
            )

        }
    }

    private fun getArticlesFailure() {
        Mockito.`when`(getArticlesMock.getArticles(any())).thenAnswer() {
            val argument = it.arguments[0]
            val response =
                argument as ((passResponseFunc: ArticleAPIResponse<List<ArticlesModel>>) -> Unit)
            response.invoke(ArticleAPIResponse.Error(message = "Error"))
        }
    }
}

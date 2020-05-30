package com.parulrabh.dbs.articledetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View.*
import androidx.appcompat.app.AlertDialog
import com.parulrabh.dbs.R
import com.parulrabh.dbs.articles.ARTICLE_AVATAR
import com.parulrabh.dbs.articles.ARTICLE_ID
import com.parulrabh.dbs.articles.ARTICLE_TITLE
import com.parulrabh.dbs.cache.CacheRepository
import com.parulrabh.dbs.model.ArticleDetailModel
import com.parulrabh.dbs.network.GetArticleDetailSync
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : AppCompatActivity(), ArticleDetailContract.View {

    var articleId: Int = 0
    lateinit var articleAvatar: String
    lateinit var articleTitle: String
    val presenter = ArticleDetailPresenter(this, GetArticleDetailSync())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        articleId = intent?.extras?.getInt(ARTICLE_ID) ?: 0
        articleAvatar = intent?.extras?.getString(ARTICLE_AVATAR) ?: ""
        articleTitle = intent?.extras?.getString(ARTICLE_TITLE) ?: ""
        supportActionBar?.hide()
        setArticleDetailData()
        presenter.setCacheRepository(CacheRepository(this.getPreferences(Context.MODE_PRIVATE)))
        presenter.fetchArticleDetail(articleId)
        txtCancel.setOnClickListener { cancelButtonClicked() }
        txtEdit.setOnClickListener { editButtonClicked() }
        buttonSave.setOnClickListener { saveButtonClicked() }
    }


    override fun displayLoader() {
        progressBarArticleDetail.visibility = VISIBLE
    }

    override fun hideLoader() {
        progressBarArticleDetail.visibility = GONE
    }

    override fun displayArticleDetail(articleData: ArticleDetailModel?) {
        //to make edit text non editable
        editTextArticleDetail.isEnabled = false
        editTextArticleDetail.setText(articleData?.text)
    }

    override fun editButtonClicked() {
        editTextArticleDetail.isEnabled = true
        buttonSave.visibility = VISIBLE
        txtCancel.visibility = VISIBLE
        txtEdit.visibility = GONE

    }

    override fun saveButtonClicked() {
        editTextArticleDetail.isEnabled = false
        txtEdit.visibility = VISIBLE
        txtCancel.visibility = GONE
        buttonSave.visibility = GONE
        //save to local cache
        presenter.saveArticleToCacheRepo(ArticleDetailModel(articleId,editTextArticleDetail.text.toString()))
        finish()
    }

    override fun cancelButtonClicked() {
        editTextArticleDetail.isEnabled = false
        buttonSave.visibility = GONE
        txtEdit.visibility = VISIBLE
        txtCancel.visibility = GONE
        finish()
    }

    override fun setArticleDetailData() {
        txtArticleDetailTitle.text = articleTitle
        Picasso.get().load(articleAvatar).into(imgArticleDetailAvatar)
    }

    override fun displayErrorDialog(message: String?) {
        progressBarArticleDetail.visibility = GONE
        //display error dialog
        displayAlertDialog(message)
    }

    private fun displayAlertDialog(message: String?) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(message ?: "Could not fetch articles details")
        alertDialog.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}

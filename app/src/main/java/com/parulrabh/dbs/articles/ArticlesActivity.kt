package com.parulrabh.dbs.articles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.parulrabh.dbs.R
import com.parulrabh.dbs.model.ArticlesModel
import com.parulrabh.dbs.network.GetArticlesSync
import kotlinx.android.synthetic.main.activity_main.*

class ArticlesActivity : AppCompatActivity() ,ArticlesContract.View{

    var presenter = ArticlesPresenter(this,GetArticlesSync())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBarArticles.visibility = VISIBLE
        //call the API to get the Articles
        presenter.fetchArticles()
    }

    private fun displayAlertDialog(message : String?){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(message ?: "Could not fetch articles")
        alertDialog.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }

    override fun displayLoader() {
        progressBarArticles.visibility = VISIBLE
    }

    override fun hideLoader() {
        progressBarArticles.visibility = GONE
    }

    override fun displayArticles(response : GetArticlesSync.ArticleResponse<List<ArticlesModel>>) {
        //set Adapter
        lstArticles.layoutManager = LinearLayoutManager(this)
        lstArticles.adapter =
            ArticlesAdapter(response.data,presenter)
    }

    override fun onArticleSelected(id : Int?) {
        TODO("Not yet implemented")
    }

    override fun displayErrorDialog(message : String?) {
        progressBarArticles.visibility = GONE
        //display error dialog
        displayAlertDialog(message)
    }
}

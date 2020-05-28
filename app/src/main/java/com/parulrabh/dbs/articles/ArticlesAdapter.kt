package com.parulrabh.dbs.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parulrabh.dbs.R
import com.parulrabh.dbs.model.ArticlesModel
import com.squareup.picasso.Picasso

class ArticlesAdapter(var articleList : List<ArticlesModel>?, var presenter : ArticlesPresenter) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle : TextView = itemView.findViewById(R.id.txtArticleTitle)
        var txtDate : TextView = itemView.findViewById(R.id.txtDate)
        var txtShortDescription : TextView = itemView.findViewById(R.id.txtShortDescription)
        var imgAvatar : ImageView = itemView.findViewById(R.id.imgAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_articles,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = articleList?.get(position)?.title
        holder.txtShortDescription.text = articleList?.get(position)?.shortDescription
        Picasso.get().load(articleList?.get(position)?.avatar).into(holder.imgAvatar)
        holder.txtDate.text = presenter.getDateDisplayFormat(articleList?.get(position)?.lastUpdate)
    }

}
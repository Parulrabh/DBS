package com.parulrabh.dbs.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.parulrabh.dbs.model.ArticleDetailModel

private const val ARTICLE_ID = "ARTICLE_ID_"

class CacheRepository(var sharedPref : SharedPreferences?) {

    fun saveToPreference(articleDetailData : ArticleDetailModel){
        with(sharedPref?.edit()){
            this?.putString("$ARTICLE_ID${articleDetailData.id}",articleDetailData.text)
            this?.commit()
        }
    }

    fun getFromPreference(articleId : Int) : String?{
        return sharedPref?.getString("$ARTICLE_ID$articleId",null)
    }

}
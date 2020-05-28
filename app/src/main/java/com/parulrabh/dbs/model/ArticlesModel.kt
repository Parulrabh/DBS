package com.parulrabh.dbs.model

import com.google.gson.annotations.SerializedName

data class ArticlesModel(
    val id: Int? = null,
    val title: String? = null,
    @SerializedName("last_update") val lastUpdate: Long? = null,
    @SerializedName("short_description")val shortDescription: String? = null,
    val avatar: String? = null){}
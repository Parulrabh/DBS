package com.parulrabh.dbs.network

sealed class ArticleAPIResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ArticleAPIResponse<T>(data)
    class Error<T>(data: T? = null, message: String) : ArticleAPIResponse<T>(data, message)
}
package com.parulrabh.dbs.mvp

interface BaseView {
    fun displayLoader()
    fun hideLoader()
    fun displayErrorDialog(message : String?)
}
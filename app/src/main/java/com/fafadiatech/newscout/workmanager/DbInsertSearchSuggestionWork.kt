package com.fafadiatech.newscout.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fafadiatech.newscout.api.ApiClient
import com.fafadiatech.newscout.api.ApiInterface
import com.fafadiatech.newscout.db.NewsDao
import com.fafadiatech.newscout.db.NewsDatabase
import com.fafadiatech.newscout.db.SearchDataEntity
import com.fafadiatech.newscout.db.SearchSuggestionEntity

class DbInsertSearchSuggestionWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    var articleNewsDao: NewsDao
    private var newsDatabase: NewsDatabase? = null
    var list = ArrayList<SearchDataEntity>()
    var apiInterface: ApiInterface

    init {
        newsDatabase = NewsDatabase.getInstance(context)
        articleNewsDao = newsDatabase!!.newsDao()
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    }

    override fun doWork(): Result {
        var searchQuery: String? = inputData.getString("search_query_text")
        var searchEntity = SearchSuggestionEntity()
        searchEntity.query = searchQuery
        articleNewsDao.insertSearchQuery(searchEntity)
        return Result.success()
    }
}
package com.bartalus.youtubedownloader.services

import android.util.Log
import android.widget.Toast
import com.bartalus.youtubedownloader.models.Response
import com.bartalus.youtubedownloader.models.Videos
import com.bartalus.youtubedownloader.utils.AppUtility
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.functions.Action
import java.util.*
import kotlin.collections.ArrayList


class ApiController() {
    private val TAG = ApiController::class.java.simpleName
    private var mService: YouTube? = null
    private var mLastError: Exception? = null
    lateinit var youtubeAPIservice:YoutubeAPIService

    init {
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
        mService = YouTube.Builder(
            transport, jsonFactory, AppUtility.mCredentials
        )
            .setApplicationName("Youtube Downloader")
            .build()


//        val mRetrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .baseUrl(AppUtility.baseURL)
//            .build()
//        youtubeAPIservice = mRetrofit.create(YoutubeAPIService::class.java)

    }

    companion object {
        private var sInstance: ApiController? = null
        fun getInstance(): ApiController {
            if (sInstance == null) {
                sInstance = ApiController()
            }
            return sInstance!!
        }
    }

    fun getStartPageVideos(): ArrayList<Videos> {
        val list = ArrayList<Videos>()
        val result = mService!!.search().list(listOf("snippet"))
                .setType(listOf("video"))
                .setOrder("viewCount")
                .setQ("sabaton")
                .setMaxResults(25)
                .execute().items as ArrayList<SearchResult>

            for (i in 0 until result.size) {
                list.add(
                    Videos(
                        result[i].id.videoId,
                        result[i].snippet.title,
                        result[i].snippet.thumbnails.high.url,
                        ""
                    )
                )

            }

        return list

    }


}
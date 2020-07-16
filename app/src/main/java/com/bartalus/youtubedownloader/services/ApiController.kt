package com.bartalus.youtubedownloader.services

import com.bartalus.youtubedownloader.utils.AppUtility
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.SearchResult


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

    fun getStartPageVideos(): ArrayList<SearchResult> {

        return mService!!.search().list(listOf("snippet"))
            .setType(listOf("video"))
            .setOrder("relevance")
                .setMaxResults(25)
                .execute().items as ArrayList<SearchResult>

    }

    fun getPlaylistItems(playlistId: String): ArrayList<PlaylistItem>{
        return mService!!.playlistItems().list(listOf("snippet"))
            .setPlaylistId(playlistId)
            .execute().items as ArrayList<PlaylistItem>
    }

    fun getStartPagePlayList(): ArrayList<SearchResult>{
        return mService!!.search().list(listOf("snippet"))
            .setOrder("relevance")
            .setType(listOf("playlist"))
            .execute().items as ArrayList<SearchResult>
    }




}
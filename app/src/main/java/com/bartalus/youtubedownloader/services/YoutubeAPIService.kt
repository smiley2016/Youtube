package com.bartalus.youtubedownloader.services

import com.bartalus.youtubedownloader.models.Response
import com.google.api.services.youtube.model.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeAPIService {

//    @GET("search?")
//    fun getStartPageVideos(
//        @Query("part") part: String,
//        @Query("order") order: String,
//        @Query("type") type: String,
//        @Query("key") key: String
//    ): Observable<Response>

    fun getStartPageVideos():Observable<ArrayList<SearchResult>>
}
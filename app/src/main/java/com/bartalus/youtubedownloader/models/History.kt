package com.bartalus.youtubedownloader.models

import java.net.URL
import java.sql.Timestamp

class History() {
    var id:Int? = null
    var url:String? = null
    var timeStamp:String? = null
    var isPlayList: Int? = null

    constructor(id:Int, url: String, timeStamp: String, isPlaylist: Int):this(){
        this.id = id
        this.url = url
        this.timeStamp = timeStamp
        this.isPlayList = isPlaylist
    }

}
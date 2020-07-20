package com.bartalus.youtubedownloader.models

class Song() {
    var id: Int? = null
    var title: String? = null
    var data: String? = null
    var displayName: String? = null
    var duration: String? = null
    var artist: String? = null

    constructor(id: Int, title: String, data: String, displayName: String, duration: String, artist: String):this(){
        this.id = id
        this.title = title
        this.data = data
        this.displayName = displayName
        this.duration = duration
        this.artist = artist
    }
}
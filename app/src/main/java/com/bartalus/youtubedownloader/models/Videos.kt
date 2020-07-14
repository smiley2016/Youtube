package com.bartalus.youtubedownloader.models

class Videos() {

    private var videoId: String? = null
    private var videoTitle: String? = null
    private var thumbnail: String? = null
    private var uploaderName: String? = null

    constructor(
        videoId: String,
        videoTitle: String,
        videoDuration: String,
        uploaderName: String
    ) : this() {
        this.videoId = videoId
        this.videoTitle = videoTitle
        this.thumbnail = videoDuration
        this.uploaderName = uploaderName
    }

    fun getVideoId(): String? {
        return this.videoId
    }

    fun getVideoTitle(): String? {
        return this.videoTitle
    }

    fun getVideoThumbnail(): String? {
        return this.thumbnail
    }

    fun getUploaderName(): String? {
        return this.uploaderName
    }

}
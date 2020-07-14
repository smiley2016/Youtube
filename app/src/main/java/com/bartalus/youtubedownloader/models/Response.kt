package com.bartalus.youtubedownloader.models

class Response {

    var kind: String? = null
    var etag: String? = null
    var nextPageToken: String? = null
    var regionCode: String? = null
    var pageInfo: PageInfo? = null
    var videoList: ArrayList<Videos>? = null

    constructor(
        kind: String?,
        etag: String?,
        nextPageToken: String?,
        regionCode: String?,
        pageInfo: PageInfo?,
        videoList: ArrayList<Videos>?
    ) {
        this.kind = kind
        this.etag = etag
        this.nextPageToken = nextPageToken
        this.regionCode = regionCode
        this.pageInfo = pageInfo
        this.videoList = videoList
    }
}
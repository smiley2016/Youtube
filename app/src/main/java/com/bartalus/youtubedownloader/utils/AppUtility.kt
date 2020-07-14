package com.bartalus.youtubedownloader.utils

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

class AppUtility {

    companion object {
        val APIkey: String = "AIzaSyABKzJL_7O9IgvfimMYPg6t1Jnjh3BgUiQ"
        val baseURL: String = "https://www.googleapis.com/youtube/v3/"
        var AccountName: String = ""
        var mCredentials: GoogleAccountCredential? = null
    }
}
package com.bartalus.youtubedownloader.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

class AppUtility {

    companion object {
        val APIkey: String = "AIzaSyABKzJL_7O9IgvfimMYPg6t1Jnjh3BgUiQ"
        val baseURL: String = "https://www.googleapis.com/youtube/v3/"
        var AccountName: String = ""
        var mCredentials: GoogleAccountCredential? = null

        fun hideKeyboard(context: Context, view: View){
            val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}
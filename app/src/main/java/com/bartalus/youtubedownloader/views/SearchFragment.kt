package com.bartalus.youtubedownloader.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartalus.youtubedownloader.R

class SearchFragment: BaseFragment() {

    private val TAG = SearchFragment::class.java.simpleName
    private lateinit var QUERY_TEXT:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_search, container, false)
        }

        return rootView

    }

    fun setQueryString(query: String){
        QUERY_TEXT = query
        Log.d(TAG, "setQueryString: $query" )
    }

    override fun onAttach(context: Context) {
        if(arguments != null){
            QUERY_TEXT = requireArguments().getString("QUERY_TEXT").toString()
        }
        super.onAttach(context)

    }
}
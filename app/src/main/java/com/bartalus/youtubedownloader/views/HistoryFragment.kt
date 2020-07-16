package com.bartalus.youtubedownloader.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartalus.youtubedownloader.R

class HistoryFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_history, container, false)
        }
        return rootView
    }
}
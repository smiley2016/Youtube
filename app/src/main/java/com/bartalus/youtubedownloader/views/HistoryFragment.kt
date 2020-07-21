package com.bartalus.youtubedownloader.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.adapters.HistoryRecyclerView
import com.bartalus.youtubedownloader.adapters.SearchRecyclerViewAdapter
import com.bartalus.youtubedownloader.models.History
import com.bartalus.youtubedownloader.utils.SQLiteLocalDatabase
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_search.*

class HistoryFragment: BaseFragment() {

    private lateinit var historyRecyclerView:HistoryRecyclerView
    private lateinit var db:SQLiteLocalDatabase

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        historyRecyclerView.addToList(fetchHistoryData())
    }

    private fun fetchHistoryData():ArrayList<History> {
        return SQLiteLocalDatabase.getInstance(requireContext()).getAllHistory()
    }

    private fun initRecyclerView() {
        val recyclerView = history_recylcer_view
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        historyRecyclerView = HistoryRecyclerView()

        recyclerView.adapter = historyRecyclerView
    }
}
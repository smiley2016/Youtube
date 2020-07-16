package com.bartalus.youtubedownloader.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.adapters.SearchRecyclerViewAdapter
import com.google.api.services.youtube.model.ResourceId
import com.google.api.services.youtube.model.SearchResult
import kotlinx.android.synthetic.main.fragment_search.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val searchRecyclerView: RecyclerView = search_recycler_view
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(this.context)
        val searchAdapter = SearchRecyclerViewAdapter()
        searchRecyclerView.adapter = searchAdapter

        fetchSearchData()

        val list : ArrayList<SearchResult> = ArrayList()
        val play = SearchResult()
        play.id = ResourceId()
        play.id.playlistId = "asdas"
        val video = SearchResult()
        video.id = ResourceId()
        list.add(play)
        list.add(video)
        list.add(play)

        searchAdapter.addToList(list)
    }

    private fun fetchSearchData() {

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
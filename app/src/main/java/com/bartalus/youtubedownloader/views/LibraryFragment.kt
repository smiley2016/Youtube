package com.bartalus.youtubedownloader.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.adapters.LibraryRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_library, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val libraryRecyclerView = library_recycler_view
        libraryRecyclerView.setHasFixedSize(true)
        libraryRecyclerView.layoutManager = LinearLayoutManager(context)

        val mLibraryRecyclerViewAdapter = LibraryRecyclerViewAdapter()

        libraryRecyclerView.adapter = mLibraryRecyclerViewAdapter

    }

}
package com.bartalus.youtubedownloader.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.models.Videos
import com.bartalus.youtubedownloader.services.ApiController
import com.bumptech.glide.Glide
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.SearchResult
import com.google.api.services.youtube.model.Video
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRecyclerViewAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mContext: Context
    private var videoList: ArrayList<SearchResult> = ArrayList()
    lateinit var mPlaylistAdapter: HomePlayListAdapter

    companion object{
        const val TYPE_PLAYLIST = 0
        const val TYPE_VIDEO = 1
    }

    fun addToVideoList(list:ArrayList<SearchResult>){
        videoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.video_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.video_title)
        lateinit var videoTitle: TextView

        @BindView(R.id.uploader_name)
        lateinit var uploaderName: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(video: SearchResult) {
            Glide.with(mContext).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            videoTitle.text = "valami"

            uploaderName.text = "valami"
        }


    }

    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @BindView(R.id.playList_recyclerView)
        lateinit var playlistRecyclerView:RecyclerView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind (){

            playlistRecyclerView.setHasFixedSize(true)
            playlistRecyclerView.layoutManager = LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false)
            mPlaylistAdapter = HomePlayListAdapter()
            playlistRecyclerView.adapter = mPlaylistAdapter

            //fetchPlayListData()

            val list = ArrayList<SearchResult>()

            list.add(SearchResult())
            list.add(SearchResult())
            list.add(SearchResult())
            list.add(SearchResult())
            list.add(SearchResult())


            mPlaylistAdapter.addToList(list)

        }

        private fun fetchPlayListData() {
            Single.fromCallable { ApiController.getInstance().getStartPagePlayList() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess{
                    mPlaylistAdapter.addToList(it)
                }
                .subscribe()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        return if(viewType == TYPE_PLAYLIST){
            val view: View = LayoutInflater.from(mContext)
                .inflate(R.layout.playlist_recycler_view, parent, false)
            PlayListViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(mContext)
                .inflate(R.layout.home_recycler_view_element, parent, false)
            HomeViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            TYPE_PLAYLIST;
        }else{
            TYPE_VIDEO;
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position) == TYPE_PLAYLIST){
            (holder as PlayListViewHolder).bind()
        }else{
            val video = videoList.get(position)
            (holder as HomeViewHolder).bind(video = video)
        }
    }


}
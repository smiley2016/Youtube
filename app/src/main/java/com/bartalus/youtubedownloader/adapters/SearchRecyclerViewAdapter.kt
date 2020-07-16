package com.bartalus.youtubedownloader.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.services.ApiController
import com.bumptech.glide.Glide
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.SearchResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context:Context
    var searchedData: ArrayList<SearchResult> = ArrayList()

    companion object{
        const val TYPE_PLAYLIST = 0
        const val TYPE_VIDEO = 1
    }

    fun addToList(list:ArrayList<SearchResult>){
        this.searchedData.addAll(list)
        notifyDataSetChanged()
    }

    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.searched_playlist_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.searched_playlist_title)
        lateinit var playlistTitle: TextView

        @BindView(R.id.searched_playlist_view)
        lateinit var playListView: TextView

        val viewType: Int = 0

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(playlist: SearchResult) {
            Glide.with(context).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            playlistTitle.text = "play"

            //fetchPlayListItemData(playlist)

            playListView.text = "videocim"

        }

        private fun fetchPlayListItemData(playlist: SearchResult){
            val playListItem = ArrayList<PlaylistItem>()
            Single.fromCallable {
                ApiController.getInstance().getPlaylistItems(playlist.id.playlistId)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    playListItem.addAll(it)
                    playListView.text = playListItem[0].snippet.title
                }
                .subscribe()
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.searched_video_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.searched_video_title)
        lateinit var videoTitle: TextView

        @BindView(R.id.searched_video_view)
        lateinit var VideoView: TextView

        var viewType:Int = 1

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(video: SearchResult) {
            Glide.with(context).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            videoTitle.text = "valami"

            VideoView.text = "video"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return if(viewType == TYPE_PLAYLIST){
            PlayListViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_search_playlist, parent,false))
        }else{
            VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_search_video,parent, false))
        }
    }

    override fun getItemCount(): Int {
        return searchedData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_PLAYLIST){
            (holder as PlayListViewHolder).bind(searchedData[position])
        }else{
            (holder as VideoViewHolder).bind(searchedData[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(searchedData[position].id.values.isEmpty()){
            TYPE_PLAYLIST
        }else{
            TYPE_VIDEO
        }
    }

}

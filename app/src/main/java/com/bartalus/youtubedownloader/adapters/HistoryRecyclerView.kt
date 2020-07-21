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
import com.bartalus.youtubedownloader.models.History
import com.bumptech.glide.Glide

class HistoryRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var context: Context
    var historyData: ArrayList<History> = ArrayList()

    companion object{
        const val TYPE_PLAYLIST = 0
        const val TYPE_VIDEO = 1
    }

    fun addToList(list:ArrayList<History>){
        this.historyData.addAll(list)
        notifyDataSetChanged()
    }

    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.searched_playlist_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.searched_playlist_title)
        lateinit var playlistTitle: TextView

        @BindView(R.id.searched_playlist_view)
        lateinit var playListView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(playlist: History) {
            Glide.with(context).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            playlistTitle.text = "playlist"

            //fetchPlayListItemData(playlist)

            playListView.text = "videocim"

        }

    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.searched_video_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.searched_video_title)
        lateinit var videoTitle: TextView

        @BindView(R.id.searched_video_view)
        lateinit var VideoView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(video: History) {
            Glide.with(context).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            videoTitle.text = "video title"

            VideoView.text = "description"
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
        return historyData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_PLAYLIST){
            (holder as PlayListViewHolder).bind(historyData[position])
        }else{
            (holder as VideoViewHolder).bind(historyData[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(historyData[position].isPlayList == 1){
           return TYPE_PLAYLIST
        }else if(historyData[position].isPlayList == 0){
           return TYPE_VIDEO
        }
        return -1
    }
}
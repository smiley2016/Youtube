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

class HomePlayListAdapter : RecyclerView.Adapter<HomePlayListAdapter.PlayListViewHolder>() {

    var playlist: ArrayList<SearchResult> = ArrayList()
    lateinit var context: Context


    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.playlist_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.playlist_title)
        lateinit var playlistTitle: TextView

        @BindView(R.id.playlist_video_title)
        lateinit var playListVideoTitle: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(playlist: SearchResult) {
            Glide.with(context).load(R.drawable.youtube).error(R.drawable.youtube).into(videoThumbnail)

            playlistTitle.text = "play"

            //fetchPlayListItemData(playlist)

            playListVideoTitle.text = "videocim"

        }

        private fun fetchPlayListItemData(playlist: SearchResult){
            val playListItem = ArrayList<PlaylistItem>()
            Single.fromCallable {
                ApiController.getInstance().getPlaylistItems(playlist.id.playlistId)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    playListItem.addAll(it)
                    playListVideoTitle.text = playListItem[0].snippet.title
                }
                .subscribe()
        }
    }

    fun addToList(mPlaylist: ArrayList<SearchResult>) {
        playlist.addAll(mPlaylist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePlayListAdapter.PlayListViewHolder {
        this.context = parent.context
        return PlayListViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.home_playlist_recycler_view_element, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: HomePlayListAdapter.PlayListViewHolder, position: Int) {
        val playlist: SearchResult = playlist[position]
        holder.bind(playlist)
    }
}
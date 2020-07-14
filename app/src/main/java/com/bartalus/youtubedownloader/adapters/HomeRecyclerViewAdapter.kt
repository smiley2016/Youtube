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
import com.bartalus.youtubedownloader.models.Videos
import com.bumptech.glide.Glide

class HomeRecyclerViewAdapter() :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder>() {

    private lateinit var mContext: Context
    private var videoList: ArrayList<Videos> = ArrayList()

    fun setList(list:ArrayList<Videos>){
        videoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder : RecyclerView.ViewHolder {
        @BindView(R.id.video_thumbnail)
        lateinit var videoThumbnail: ImageView

        @BindView(R.id.video_title)
        lateinit var videoTitle: TextView

        @BindView(R.id.uploader_name)
        lateinit var uploaderName: TextView

        constructor(itemView: View) : super(itemView) {
            ButterKnife.bind(this, itemView)
        }

        fun bind(video: Videos) {
            Glide.with(mContext).load(video.getVideoThumbnail()).into(videoThumbnail)

            videoTitle.text = video.getVideoTitle()

            uploaderName.text = video.getUploaderName()
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        mContext = parent.context
        val view: View = LayoutInflater.from(mContext)
            .inflate(R.layout.home_recycler_view_element, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val video = videoList.get(position)
        holder.bind(video)
    }


}
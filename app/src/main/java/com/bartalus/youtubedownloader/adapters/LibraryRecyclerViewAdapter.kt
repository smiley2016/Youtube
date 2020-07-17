package com.bartalus.youtubedownloader.adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.models.Song
import com.bumptech.glide.Glide
import io.reactivex.Single
import kotlinx.android.synthetic.main.library_popup_dialog.*
import java.io.FileDescriptor
import java.io.FileInputStream
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

class LibraryRecyclerViewAdapter : RecyclerView.Adapter<LibraryRecyclerViewAdapter.LibraryViewHolder>() {

    private val songList: ArrayList<Song> = ArrayList()
    private lateinit var context : Context

    fun addToList(list:ArrayList<Song>){
        songList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryRecyclerViewAdapter.LibraryViewHolder {
        this.context = parent.context
        return LibraryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.library_recyler_view_elment, parent, false))
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: LibraryRecyclerViewAdapter.LibraryViewHolder, position: Int) {
        val song: Song = songList[position]
        holder.bind(song)
    }

    inner class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.library_item)
        lateinit var libraryItem:RelativeLayout
        @BindView(R.id.library_item_thumbnail)
        lateinit var libraryItemThumbnail:ImageView
        @BindView(R.id.library_item_title)
        lateinit var libraryItemTitle:TextView
        @BindView(R.id.library_item_duration)
        lateinit var libraryItemDuration:TextView

        val handler = android.os.Handler()

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(song: Song){
            libraryItemTitle.text = song.displayName
            libraryItemDuration.text = durationBuilder(song.duration!!)

            libraryItem.setOnClickListener{
                val dialog: Dialog = Dialog(context)
                dialog.setContentView(R.layout.library_popup_dialog)

                val playButton = dialog.dialog_playButton
                val seekBar = dialog.dialog_seekbar

                seekBar.progress = 0
                seekBar.max = 100

                dialog.show()

                val mediaPlayer = MediaPlayer()

                mediaPlayer.setDataSource(song.data)


                mediaPlayer.prepare()
                mediaPlayer.setVolume(1f,1f)
                mediaPlayer.isLooping = true
                mediaPlayer.start()

                val mediaFileLengthInMillis = mediaPlayer.duration

                playButton.setOnClickListener{

                    if(mediaPlayer.isPlaying){
                        Glide.with(context).load(R.drawable.ic_baseline_play_arrow_25).into(playButton)

                        (context as Activity).getPreferences(Context.MODE_PRIVATE)
                            .edit()
                            .putInt("SEEK_TO", mediaPlayer.currentPosition)
                            .apply()

                        mediaPlayer.stop()
                    }else{
                        Glide.with(context).load(R.drawable.ic_baseline_pause_25).into(playButton)

                        val seekTo = (context as Activity).getPreferences(Context.MODE_PRIVATE).getInt("SEEK_TO",0)

                        mediaPlayer.prepare()
                        mediaPlayer.seekTo(seekTo)
                        mediaPlayer.start()
                    }



                }

                dialog.setOnDismissListener{
                    mediaPlayer.stop()
                    mediaPlayer.release()
                }

                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekbar: SeekBar?, p1: Int, p2: Boolean) {
                        if(p2){
                            seekBar.setProgress(p1, true)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }
                })
            }

        }


        private fun durationBuilder(duration: String):String{
            val durationBuilder = StringBuilder()
            val hours = TimeUnit.MILLISECONDS.toHours(duration.toLong())

            if(hours > 0 ){
                durationBuilder.append(hours).append(":")
            }

            val minutes = TimeUnit.MILLISECONDS.toMinutes (duration.toLong()
                - TimeUnit.HOURS.toMillis(hours))

            durationBuilder.append(minutes).append(":")

            val seconds = TimeUnit.MILLISECONDS.toSeconds(
                duration.toLong() - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes))

            durationBuilder.append(seconds)
            return durationBuilder.toString()
        }

    }

}

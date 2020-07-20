package com.bartalus.youtubedownloader.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import com.bartalus.youtubedownloader.R
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.library_popup_dialog.*
import java.lang.StringBuilder
import java.sql.Time
import java.util.concurrent.TimeUnit

class MediaPlayerDialogFragment : DialogFragment(), DialogInterface.OnDismissListener,
    View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {

    companion object{
        lateinit var SONG_PATH: String
        lateinit var SONG_TITLE: String
        lateinit var SONG_ARTIST: String
        private val TAG: String = MediaPlayerDialogFragment::class.java.simpleName
    }

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: ImageView
    private lateinit var mSeekBar: SeekBar
    private lateinit var mDialog: Dialog
    private lateinit var mTitle: TextView
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var playImage:Int = R.drawable.ic_baseline_pause_25

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            SONG_PATH = requireArguments().getString("SONG_PATH", "")
            SONG_TITLE = requireArguments().getString("SONG_TITLE", "")
            SONG_ARTIST = requireArguments().getString("SONG_ARTIST", "")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = super.onCreateDialog(savedInstanceState)

        mDialog.setContentView(R.layout.library_popup_dialog)

        mDialog.window!!.setLayout(requireActivity().window.decorView.width, ConstraintSet.WRAP_CONTENT)

        initMediaPlayer()

        initDialogViews()

        return mDialog
    }

    fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)

        mediaPlayer.setDataSource(SONG_PATH)

        mediaPlayer.prepare()
        mediaPlayer.setVolume(1f, 1f)
        mediaPlayer.start()
    }

    private fun initDialogViews() {

        mDialog.setOnDismissListener(this)

        playButton = mDialog.dialog_playButton
        playButton.setOnClickListener(this)

        mSeekBar = mDialog.dialog_seek_bar
        mSeekBar.setOnSeekBarChangeListener(this)
        mSeekBar.progress = 0
        mSeekBar.max = mediaPlayer.duration
        updateProgressBarState()

        mTitle = mDialog.dialog_song_title
        mTitle.text = StringBuilder().append(SONG_TITLE).toString()

        playImage = R.drawable.ic_baseline_pause_25
    }

    private fun updateProgressBarState() {
       val disposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
           .map {
               mSeekBar.progress = mediaPlayer.currentPosition
           }
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe()

        compositeDisposable.add(disposable)
    }

    override fun onDismiss(dialog: DialogInterface) {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
        compositeDisposable.dispose()
        super.onDismiss(dialog)
    }


    override fun onClick(mView: View?) {
        if(mView!!.id == R.id.dialog_playButton){
            if(playImage != R.drawable.ic_baseline_refresh_25 ){
                if (mediaPlayer.isPlaying) {
                    Glide.with(requireContext()).load(R.drawable.ic_baseline_play_arrow_25)
                        .into(playButton)
                    playImage = R.drawable.ic_baseline_play_arrow_25

                    savePreferences(mediaPlayer.currentPosition)

                    mediaPlayer.pause()
                } else {
                    Glide.with(requireContext()).load(R.drawable.ic_baseline_pause_25).into(playButton)

                    playImage = R.drawable.ic_baseline_pause_25

                    val seekTo = readPreferences()

                    mediaPlayer.seekTo(seekTo)
                    mediaPlayer.start()
                }
            }else{
                mSeekBar.progress = 0

                Glide.with(requireContext()).load(R.drawable.ic_baseline_pause_25).into(playButton)
                playImage = R.drawable.ic_baseline_pause_25


                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            }

        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        Log.d(Companion.TAG, "onProgressChanged: $p1 $p2")
        if(p2 && p1 <= mediaPlayer.duration){
            mediaPlayer.seekTo(p1)
            if(playImage == R.drawable.ic_baseline_pause_25){
                mediaPlayer.start()
            }else if(playImage == R.drawable.ic_baseline_refresh_25){
                Glide.with(requireContext()).load(R.drawable.ic_baseline_play_arrow_25).into(playButton)
                playImage = R.drawable.ic_baseline_play_arrow_25
            }

            savePreferences(p1)

        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onCompletion(p0: MediaPlayer?) {
        Glide.with(requireContext()).load(R.drawable.ic_baseline_refresh_25).into(mDialog.dialog_playButton)
        playImage = R.drawable.ic_baseline_refresh_25

        savePreferences(0)
    }

    private fun savePreferences(time: Int, tag: String = "SEEK_TO"){
        (context as Activity).getPreferences(Context.MODE_PRIVATE)
            .edit()
            .putInt(tag, time)
            .apply()
    }

    private fun readPreferences():Int{
        return (context as Activity).getPreferences(Context.MODE_PRIVATE).getInt("SEEK_TO", 0)
    }


}
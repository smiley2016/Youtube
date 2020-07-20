package com.bartalus.youtubedownloader.views

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartalus.youtubedownloader.MainActivity
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.adapters.LibraryRecyclerViewAdapter
import com.bartalus.youtubedownloader.models.Song
import kotlinx.android.synthetic.main.fragment_library.*
import java.lang.Exception


class LibraryFragment: BaseFragment() {

    private lateinit var mLibraryRecyclerViewAdapter:LibraryRecyclerViewAdapter
    private lateinit var cursor: Cursor

    companion object{
        const val READ_FROM_EXTERNAL_STORAGE_PERMISSION = 1
    }

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

        val permissionCheck = ContextCompat.checkSelfPermission(this.context as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.context as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_FROM_EXTERNAL_STORAGE_PERMISSION)
        }else{
            fetchMusicsFromTheDevice()
        }
    }

    private fun fetchMusicsFromTheDevice() {
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST
        )

        val contentResolver = (context as MainActivity).contentResolver

        cursor = (this.context as Activity).managedQuery(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )

        val songs: ArrayList<Song> = ArrayList()
        try{

            while(cursor.moveToNext()) {
                songs.add(Song(
                    cursor.getString(0).toInt(),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5))
                )
            }

            mLibraryRecyclerViewAdapter.addToList(songs)

        }catch (ex: Exception){
            ex.printStackTrace()
            ex.message
        }

    }

    private fun initRecyclerView() {
        val libraryRecyclerView = library_recycler_view
        libraryRecyclerView.setHasFixedSize(true)
        libraryRecyclerView.layoutManager = LinearLayoutManager(context)

        mLibraryRecyclerViewAdapter = LibraryRecyclerViewAdapter()

        libraryRecyclerView.adapter = mLibraryRecyclerViewAdapter

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(LibraryFragment::class.java.name, "onRequestPermissionsResult: " + requestCode)

        if(requestCode == READ_FROM_EXTERNAL_STORAGE_PERMISSION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchMusicsFromTheDevice()
            }else{
                Toast.makeText(this.context, "You have to grant read permission to list your music!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        cursor.close()
        super.onDestroy()
    }

}
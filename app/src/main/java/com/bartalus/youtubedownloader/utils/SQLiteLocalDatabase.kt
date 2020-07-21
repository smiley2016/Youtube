package com.bartalus.youtubedownloader.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bartalus.youtubedownloader.models.History
import java.lang.Exception
import java.sql.SQLException
import kotlin.properties.Delegates

class SQLiteLocalDatabase (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        //Class name
        private val TAG = SQLiteLocalDatabase::class.java.simpleName

        //Database Info
        private const val DATABASE_NAME = "HistoryDatabase"
        private const val DATABASE_VERSION = 10

        //Table name
        private const val TABLE_HISTORY = "history"

        //History table columns
        private const val KEY_HISTORY_ID = "history_id"
        private const val KEY_HISTORY_URL = "history_url"
        private const val KEY_HISTORY_DATE = "history_date"
        private const val KEY_HISTORY_IS_PLAYLIST = "is_playlist"

        private var sInstance:SQLiteLocalDatabase? = null

        fun getInstance(context: Context):SQLiteLocalDatabase {
            if (sInstance == null){
                sInstance = SQLiteLocalDatabase(context)
            }
            return sInstance!!
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createHistoryTable = "CREATE TABLE " + TABLE_HISTORY +
                "(" +
                    KEY_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_HISTORY_URL + " VARCHAR2(100), " +
                    KEY_HISTORY_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    KEY_HISTORY_IS_PLAYLIST + " INTEGER NOT NULL " +
                ")"

        try{
            db!!.execSQL(createHistoryTable)
        }catch (e: SQLException){
            Log.d(TAG, "onCreateException: ${e.message}")
        }



        Log.d(TAG, "DB create success")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
            onCreate(db)

            Log.d(TAG, "DB Upgrade success")
        }


    }

    fun addHistory(url: String, isPlaylist:Int){
        var db:SQLiteDatabase? = null
        try{
            db = writableDatabase

            db.beginTransaction()
            val values = ContentValues()
            values.put(KEY_HISTORY_URL, url)
            values.put(KEY_HISTORY_IS_PLAYLIST, isPlaylist)

            db.insertOrThrow(TABLE_HISTORY, null, values)
            db.setTransactionSuccessful()
        }catch (e: Exception){
            e.printStackTrace()
            Log.d(TAG, "addHistoryException: "+e.message)
        }finally {
            db!!.endTransaction()
        }
    }

    fun getAllHistory():ArrayList<History>{
        val history = ArrayList<History>()

        val historySelectQuery = "SELECT * FROM $TABLE_HISTORY"

        val db = readableDatabase

        val cursor = db.rawQuery(historySelectQuery, null)

        try {
            if(cursor.moveToFirst()){
                do{
                    val hist = History()
                    hist.id = cursor.getString(0).toInt()
                    hist.url = cursor.getString(1)
                    hist.timeStamp = cursor.getString(2)
                    hist.isPlayList = cursor.getString(3).toInt()

                    history.add(hist)
                }while (cursor.moveToNext())
            }
        }catch (e: Exception){
            Log.d(TAG, "getAllHistoryException: ${e.message}")
            e.printStackTrace()
            e.message
        }finally {
            if(cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }

        return history
    }


}
package com.bartalus.youtubedownloader.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bartalus.youtubedownloader.MainActivity
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.views.*

class FragmentNavigation() {
    private val TAG = "FragmentNavigation"
    private var mMainActivityFragmentContainer: Int? = null
    private var mFragmentTransaction: FragmentTransaction? = null
    private var ADD_OR_REPLACE = ""

    init {
        mMainActivityFragmentContainer = R.id.fragment_holder
    }

    private fun setFragmentArguments(fragment: Fragment, bundle: Bundle): Fragment {
        fragment.arguments = bundle
        return fragment
    }

    fun getCurrentFragment(): Fragment? {
        return mFragmentManager!!.findFragmentById(mMainActivityFragmentContainer!!)
    }

    private fun replaceFragment(fragment: Fragment, container: Int, addToBackstack: Boolean = true, addOrReplace:String = "replace") {
        mFragmentTransaction = mFragmentManager!!.beginTransaction()

        if(addOrReplace == "add"){
            mFragmentTransaction!!.add(container, fragment, fragment.tag)
        }else{
            mFragmentTransaction!!.replace(container, fragment, fragment.tag)
        }

        if(addToBackstack){
            mFragmentTransaction!!.addToBackStack(fragment::class.java.simpleName)
        }

        try {
            mFragmentTransaction!!.commit()

            Log.d(TAG, "Fragment ${fragment::class.java.simpleName} replaced and committed!")
        } catch (ex: Exception) {
            ex.printStackTrace()
            ex.message
        }

    }

    fun showHomeFragment(addToBackstack: Boolean) {
        val currentFragment: Fragment = setFragmentArguments(HomeFragment(), Bundle())
        replaceFragment(currentFragment, mMainActivityFragmentContainer!!, addToBackstack)
    }

    fun showLibraryFragment() {
        val currentFragment: Fragment = setFragmentArguments(LibraryFragment(), Bundle())
        replaceFragment(currentFragment, mMainActivityFragmentContainer!!)
    }

    fun showHistoryFragment(){
        val currentFragment: Fragment = setFragmentArguments(HistoryFragment(), Bundle())
        replaceFragment(currentFragment, mMainActivityFragmentContainer!!)
    }

    fun showSearchFragment(bundle: Bundle){
        val currentFragment: Fragment = setFragmentArguments(SearchFragment(), bundle)
        replaceFragment(currentFragment, mMainActivityFragmentContainer!!)
    }

    fun showMediaPlayerDialog(bundle: Bundle){
        mFragmentTransaction = mFragmentManager!!.beginTransaction()
        val currentFragment: Fragment = setFragmentArguments(MediaPlayerDialogFragment(), bundle)
        (currentFragment as MediaPlayerDialogFragment).show(mFragmentTransaction!!, currentFragment.tag)

    }

    companion object {
        private var sInstance: FragmentNavigation? = null
        private var mFragmentManager: FragmentManager? = null

        fun getInstance(context: Context): FragmentNavigation {
            if (sInstance == null) {
                sInstance = FragmentNavigation()
            }
            initAttributes(context)
            return sInstance!!;
        }

        private fun initAttributes(context: Context) {
            mFragmentManager = (context as MainActivity).supportFragmentManager
        }
    }
}
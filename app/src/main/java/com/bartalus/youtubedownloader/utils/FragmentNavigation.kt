package com.bartalus.youtubedownloader.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bartalus.youtubedownloader.MainActivity
import com.bartalus.youtubedownloader.R
import com.bartalus.youtubedownloader.views.HomeFragment

class FragmentNavigation() {
    private val TAG = "FragmentNavigation"
    private var mFragmentManager: FragmentManager? = null
    private var mMainActivityFragmentContainer: Int? = null
    private var mFragmentTransaction: FragmentTransaction? = null

    constructor(context: Context) : this() {
        mMainActivityFragmentContainer = R.id.fragment_holder
        initAttributes(context)
    }

    fun setFragmentArguments(fragment: Fragment, bundle: Bundle): Fragment {
        fragment.arguments = bundle
        return fragment
    }

    fun initAttributes(context: Context) {
        mFragmentManager = (context as MainActivity).supportFragmentManager
    }

    fun getCurrentFragment(): Fragment? {
        return mFragmentManager!!.findFragmentById(mMainActivityFragmentContainer!!)
    }

    fun replaceFragment(fragment: Fragment, container: Int) {
        mFragmentTransaction = mFragmentManager!!.beginTransaction()

        mFragmentTransaction!!.setReorderingAllowed(false)

        mFragmentTransaction!!.replace(container, fragment, fragment.tag)

        mFragmentTransaction!!.addToBackStack(fragment::class.java.simpleName)

        try {
            mFragmentTransaction!!.commit()

            Log.d(TAG, "Fragment replaced and committed!")
        } catch (ex: Exception) {
            ex.printStackTrace()
            ex.message
        }

    }

    fun showHomeFragment() {
        val currentFragment: Fragment = setFragmentArguments(HomeFragment(), Bundle())
        replaceFragment(currentFragment, mMainActivityFragmentContainer!!)
    }

    companion object {
        private var sInstance: FragmentNavigation? = null

        fun getInstance(context: Context): FragmentNavigation {
            if (sInstance == null) {
                sInstance = FragmentNavigation(context)
            }
            return sInstance!!;
        }
    }
}
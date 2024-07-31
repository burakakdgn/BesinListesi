package com.brk.besinkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.brk.besinkitabi.roomdb.BesinDataBase

class OzelSharedPreferences {


    companion object {

        private val ZAMAN = "zaman"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance: OzelSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferencesOlustur(context).also {
                instance = it
            }
        }

        private fun ozelSharedPreferencesOlustur(context: Context) : OzelSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  OzelSharedPreferences()
        }

    }
    fun zamaniKaydet(zaman : Long) {
        sharedPreferences?.edit()?.putLong(ZAMAN,zaman)?.apply()

    }

    fun zamaniAl() = sharedPreferences?.getLong(ZAMAN,0)
}
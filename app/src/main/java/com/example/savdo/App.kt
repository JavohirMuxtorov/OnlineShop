package com.example.savdo

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.savdo.db.AppDatabase
import com.orhanobut.hawk.Hawk

class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabase.initDatabase(this)
    }
}
package com.example.shacklehotelbuddy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShackleHotel: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
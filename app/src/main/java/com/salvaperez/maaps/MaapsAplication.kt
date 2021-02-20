package com.salvaperez.maaps

import android.app.Application
import com.salvaperez.maaps.data.di.initDI

class MaapsAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
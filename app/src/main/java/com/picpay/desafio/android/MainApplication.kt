package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.MainModule
import com.picpay.desafio.android.utils.KoinUtilities
import org.koin.core.context.loadKoinModules


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        KoinUtilities.loadKoin(applicationContext)
        loadKoinModules(MainModule.allModules())
    }


}
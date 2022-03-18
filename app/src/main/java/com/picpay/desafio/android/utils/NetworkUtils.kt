package com.picpay.desafio.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils(private val context: Context) {
    //Verifica se usuário tem acesso a internet
    fun hasInternet(): Boolean {
        val connMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
            capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            //Usei o "@Suppress("DEPRECATION")", porque sem ele não tem como fazer a verificação em versões que são inferiores ao "Q"
            @Suppress("DEPRECATION")
            connMgr.activeNetworkInfo?.isConnected == true
        }
    }
}
package com.edcan.chanbobsinse.utils

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by eka on 2018. 2. 23..
 */
object NetworkHelper {
    private const val url = ""
    private const val mapUrl = "https://openapi.naver.com/"
    private const val port = 0

    var retrofit: Retrofit? = null

    val mapInstance: NetworkAPI
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(mapUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!.create<NetworkAPI>(NetworkAPI::class.java)
        }
    val networkInstance: NetworkAPI
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(url + ":" + port)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!.create<NetworkAPI>(NetworkAPI::class.java)
        }

    fun returnNetworkState(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}
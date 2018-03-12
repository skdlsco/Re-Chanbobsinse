package com.edcan.chanbobsinse.utils

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by eka on 2018. 2. 23..
 */
object NetworkHelper {
    private const val url = "http://iwin247.info:3355"
    private const val mapUrl = "https://openapi.naver.com/"

    var mapRetrofit: Retrofit? = null
    var retrofit: Retrofit? = null
    val mapInstance: NetworkAPI
        get() {
            if (mapRetrofit == null) {
                mapRetrofit = Retrofit.Builder()
                        .baseUrl(mapUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return mapRetrofit!!.create<NetworkAPI>(NetworkAPI::class.java)
        }
    val restaurantInstance: NetworkAPI
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(url)
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
package com.edcan.chanbobsinse.view.map

import android.os.Handler
import android.util.Log
import com.edcan.chanbobsinse.utils.NetworkHelper
import com.google.android.gms.maps.model.LatLng
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by eka on 2018. 3. 6..
 */
class MapModel {

    var address = ""
    var latLng = LatLng(0.0, 0.0)

    var onAddressChanged: (String) -> Unit = {}
    var onLatLngChanged: (LatLng) -> Unit = {}
    private val handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            latLngToAddress()
            handler.postDelayed(this, 3000)
        }
    }

    fun latLngToAddress() {
        var returnAddress = ""
        NetworkHelper.mapInstance.changeFromLatLng("${latLng.longitude}, ${latLng.latitude}",
                "WAtJVVGhyrZDGy4TGVXW",
                "AtG79bTvJh").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                try {
                    val json = JSONObject(response?.body()?.string())
                            .getJSONObject("result")

                            .getJSONArray("items").getJSONObject(0).getJSONObject("addrdetail")
                    returnAddress = "${formatSido(json.getString("sido"))} ${json.getString("sigugun")} " +
                            "${json.getString("dongmyun")} ${json.getString("rest")}"
                    address = returnAddress
                    onAddressChanged.invoke(address)
                } catch (e: Exception) {
                    Log.e("asdf", e.message)
                    return
                }
            }
        })
    }

    fun addressToLatLng(address: String) {
        NetworkHelper.mapInstance.changeFromAddress(address,
                "WAtJVVGhyrZDGy4TGVXW",
                "AtG79bTvJh").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("addressToLatLng", "Failed")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                try {
                    val json = JSONObject(response?.body()?.string())
                            .getJSONObject("result")
                            .getJSONArray("items").getJSONObject(0).getJSONObject("point")
                    latLng = LatLng(json.getDouble("y"), json.getDouble("x"))
                    onLatLngChanged.invoke(latLng)
                } catch (e: Exception) {
                    Log.e("addressToLatLng", e.message)
                    return
                }
            }
        })
    }

    fun formatSido(sido: String) = when (sido) {
        "서울특별시" ->
            "서울시"
        else ->
            sido
    }

    fun startAddressUpdate() {
        handler.postDelayed(runnable, 3000)
    }

    fun stopAddressUpdate() {
        handler.removeCallbacks(runnable)
    }
}
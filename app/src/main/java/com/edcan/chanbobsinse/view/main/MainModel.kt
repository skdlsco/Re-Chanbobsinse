package com.edcan.chanbobsinse.view.main

import android.content.Context
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.utils.GpsInfo
import com.edcan.chanbobsinse.utils.NetworkHelper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by eka on 2018. 2. 21..
 */
class MainModel {
    var gpsInfo: GpsInfo? = null
    var address = ""
    var categories = ArrayList<Category>().apply {
        add(Category().apply {
            title = "한식"
            discription = "오늘따라 집밥이 그리울 때,\n밖에서 먹는 우리집의 맛"
        })
        add(Category().apply {
            title = "일식"
            discription = "초밥, 돈까스, 회, 카레 등등…\n익숙하지만 언제든 먹고싶은 음식"
        })
        add(Category().apply {
            title = "중식"
            discription = "배달음식의 대명사,\n여기 짜장 둘 짬뽕 하나요~"
        })
        add(Category().apply {
            title = "양식"
            discription = "평소에 먹지 않던 이색적인 맛을\n경험하고 싶을 때"
        })
        add(Category().apply {
            title = "치킨"
            discription = "언제나 옳으신 그 분,\n치느님"
        })
        add(Category().apply {
            title = "피자"
            discription = "영원한 치느님의 동무,\n쭈욱 늘어나는 치즈를 맘껏 먹고싶을 때"
        })
        add(Category().apply {
            title = "보족"
            discription = "고기가 땡기니\n쌈 채소 섭취를 명분으로"
        })
        add(Category().apply {
            title = "분식"
            discription = "한식의 연장선,\n우리 모두의 친구"
        })
    }

    fun initGpsInfo(context: Context) {
        gpsInfo = GpsInfo(context)
    }

    fun makeAddress(callback: () -> Unit) {
        gpsInfo!!.getLocation()
        val latlng = "${gpsInfo!!.lon},${gpsInfo!!.lat}"
        NetworkHelper.mapInstance.changeAddress(latlng, "WAtJVVGhyrZDGy4TGVXW", "AtG79bTvJh").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val json = JSONObject(response?.body()!!.string())
                        .getJSONObject("result")
                        .getJSONArray("items").getJSONObject(0).getJSONObject("addrdetail")
                address = "${formatSido(json.getString("sido"))} ${json.getString("sigugun")} ${json.getString("dongmyun")}"
                callback.invoke()
            }
        })
    }

    fun formatSido(sido: String) = when (sido) {
        "서울특별시" ->
            "서울시"
        else ->
            sido
    }


    fun getSelectedItem() = categories.filter { it.isSelected }
}
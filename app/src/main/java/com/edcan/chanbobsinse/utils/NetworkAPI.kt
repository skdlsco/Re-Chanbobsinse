package com.edcan.chanbobsinse.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by eka on 2018. 2. 23..
 */
interface NetworkAPI {

    @GET("/v1/map/reversegeocode")
    fun changeFromLatLng(@Query("query") latlng: String,
                         @Header("X-Naver-Client-Id") id: String,
                         @Header("X-Naver-Client-Secret") secret: String): Call<ResponseBody>

    @GET("/v1/map/geocode")
    fun changeFromAddress(@Query("query") address: String,
                          @Header("X-Naver-Client-Id") id: String,
                          @Header("X-Naver-Client-Secret") secret: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/search")
    fun searchingRestaurants(@Field("maxPrice") maxPrice: Int,
                             @Field("minPrice") minPrice: Int,
                             @Field("pm") range: Int,
                             @Field("category") categories: ArrayList<String>): Call<ResponseBody>
}
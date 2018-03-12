package com.edcan.chanbobsinse.view.searching

import android.util.Log
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.utils.NetworkHelper
import com.google.android.gms.maps.model.LatLng
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by eka on 2018. 3. 12..
 */
class SearchingModel {
    var address = ""
    var price = Price()
    var latLng = LatLng(0.0, 0.0)
    var categories = ArrayList<Category>()
    var restaurants = ArrayList<Restaurant>()
    fun searching(onFinish: (String) -> Unit) {
        val max = price.max.replace(",", "").toInt()
        val min = price.min.replace(",", "").toInt()
        var range = 10
        try {
            range = price.range.replace(",", "").toInt()
        } catch (e: Exception) {

        }
        val nCategories = ArrayList<String>()
        categories.forEach {
            nCategories.add(it.title)
        }
        NetworkHelper.restaurantInstance.searchingRestaurants(max, min, range, nCategories)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.e("failed", "asdf")
                        onFinish.invoke("error")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        var json = JSONArray()
                        try {
                        val str = JSONObject(response?.body()?.string()).getString("result")
                            json = JSONArray(str)
                        } catch (e: Exception) {
                            onFinish.invoke("error")
                            return
                        }
                        (0 until json.length()).forEach {
                            val restaurant = Restaurant()
                            json.getJSONObject(it).let {
                                restaurant.address = it.getString("address")
                                restaurant.title = it.getString("name")
                                restaurant.categories.add(Category().apply { title = it.getString("category") })
                                val menus = it.getJSONArray("menus")
                                (0 until menus.length()).forEach {
                                    menus.getJSONObject(it).let {
                                        restaurant.menus.add(Menu().apply {
                                            title = it.getString("name")
                                            price = it.getInt("price")
                                        })
                                    }
                                }
                            }
                            restaurants.add(restaurant)
                        }
                        onFinish.invoke("success")
                    }
                })
    }
}
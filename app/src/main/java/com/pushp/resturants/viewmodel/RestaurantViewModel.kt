package com.pushp.resturants.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.pushp.resturants.model.MenuData
import com.pushp.resturants.model.Rstrnts
import com.pushp.resturants.repository.ResturantRepository


class RestaurantViewModel : ViewModel() {

    private var liveDataRestaurants: LiveData<List<String>>? = null

    fun insertData(context: Context, resturants: Rstrnts) {
        ResturantRepository.insertData(context, resturants)
    }

    fun insertData(context: Context, menus: MenuData) {
        ResturantRepository.insertData(context, menus)
    }

    fun getRestaurantsDetails(context: Context, text: String): LiveData<List<String>>? {
        liveDataRestaurants = ResturantRepository.getRestaurantDetails(context, text)
        return liveDataRestaurants
    }

    fun getAllDetails(context: Context): LiveData<List<String>>? {
        liveDataRestaurants = ResturantRepository.checkAllRestaurantDetails(context)
        return liveDataRestaurants
    }

}
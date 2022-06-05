package com.pushp.resturants.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.pushp.resturants.model.MenuData
import com.pushp.resturants.model.Rstrnts
import com.pushp.resturants.repository.ResturantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val restaurantRepository: ResturantRepository
) : ViewModel() {

    private var liveDataRestaurants: LiveData<List<String>>? = null

    fun insertData(restaurants: Rstrnts) {
        restaurantRepository.insertData(restaurants)
    }

    fun insertData(menus: MenuData) {
        restaurantRepository.insertData(menus)
    }

    fun getRestaurantsDetails(text: String): LiveData<List<String>>? {
        liveDataRestaurants = restaurantRepository.getRestaurantDetails(text)
        return liveDataRestaurants
    }

    fun getAllDetails(): LiveData<List<String>>? {
        liveDataRestaurants = restaurantRepository.checkAllRestaurantDetails()
        return liveDataRestaurants
    }

}
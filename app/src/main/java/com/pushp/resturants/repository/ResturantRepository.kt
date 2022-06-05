package com.pushp.resturants.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.pushp.resturants.model.MenuData
import com.pushp.resturants.model.MenuItem
import com.pushp.resturants.model.table.ResturantTableModel
import com.pushp.resturants.model.Rstrnts
import com.pushp.resturants.room.ResturantDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResturantRepository @Inject constructor(
    private val restaurantDatabase: ResturantDatabase
) {
    private var resTableModel: LiveData<List<String>>? = null


    fun insertData(restaurants: Rstrnts) {

        CoroutineScope(IO).launch {
            restaurants.restaurants.forEach { restaurant ->
                var rating = 0
                var comment = ""
                var date = ""
                var rName = ""

                restaurant.reviews.forEach {
                    rating = it.rating
                    comment = it.comments
                    date = it.date
                    rName = it.name
                }

                val restaurantsDetails = ResturantTableModel(
                    restaurant.name,
                    restaurant.id,
                    restaurant.operating_hours.toString(),
                    restaurant.latlng.toString(),
                    "",
                    "",
                    "",
                    rName,
                    restaurant.cuisine_type,
                    rating.toString(),
                    comment,
                    restaurant.address,
                    restaurant.neighborhood,
                    date
                )
                restaurantDatabase.resturantDao.InsertData(restaurantsDetails)
            }

        }

    }

    fun insertData(menuData: MenuData) {


        CoroutineScope(IO).launch {
            menuData.menus.forEach { menu ->
                var name = ""
                var menuItem: List<MenuItem>? = null
                var description = ""
                var price = ""
                var itemName = ""
                menu.categories.forEach {
                    name = it.name
                    menuItem = it.menuItem
                }
                menuItem?.forEach {
                    description = it.description
                    itemName = it.name
                    price = it.price
                }
                val menuDetails = ResturantTableModel(
                    name,
                    menu.restaurantId,
                    "",
                    "",
                    itemName,
                    description,
                    price,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
                restaurantDatabase.resturantDao.InsertData(menuDetails)
            }

        }

    }

    fun getRestaurantDetails(text: String): LiveData<List<String>>? {

        text.let {
            if (it.isNotEmpty()) {
                resTableModel = restaurantDatabase.resturantDao.getAllData(text)
            }
        }

        return resTableModel
    }

    fun checkAllRestaurantDetails(): LiveData<List<String>>? {

        resTableModel = restaurantDatabase.resturantDao.checkAllData()

        return resTableModel
    }


}
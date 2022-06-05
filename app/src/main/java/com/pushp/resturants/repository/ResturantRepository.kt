package com.pushp.resturants.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.pushp.resturants.model.MenuData
import com.pushp.resturants.model.MenuItem
import com.pushp.resturants.model.table.ResturantTableModel
import com.pushp.resturants.model.Rstrnts
import com.pushp.resturants.room.ResturantDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ResturantRepository {

    companion object {

        private var resturantDatabase: ResturantDatabase? = null

        private var resTableModel: LiveData<List<String>>? = null

        private fun initializeDB(context: Context): ResturantDatabase {
            return ResturantDatabase.getDatasetClient(context)
        }

        fun insertData(context: Context, restaurants: Rstrnts) {

            resturantDatabase = initializeDB(context)

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
                    resturantDatabase?.resturantDao()?.InsertData(restaurantsDetails)
                }

            }

        }

        fun insertData(context: Context, menuData: MenuData) {

            resturantDatabase = initializeDB(context)

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
                    resturantDatabase?.resturantDao()?.InsertData(menuDetails)
                }

            }

        }

        fun getRestaurantDetails(context: Context, text: String): LiveData<List<String>>? {

            resturantDatabase = initializeDB(context)

            text.let {
                if (it.isNotEmpty()) {
                    resTableModel = resturantDatabase?.resturantDao()?.getAllData(text)
                }
            }

            return resTableModel
        }

        fun checkAllRestaurantDetails(context: Context): LiveData<List<String>>? {

            resturantDatabase = initializeDB(context)

            resTableModel = resturantDatabase?.resturantDao()?.checkAllData()

            return resTableModel
        }


    }
}
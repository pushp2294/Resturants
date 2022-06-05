package com.pushp.resturants.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pushp.resturants.R
import com.pushp.resturants.model.MenuData
import com.pushp.resturants.model.Rstrnts
import com.pushp.resturants.viewmodel.RestaurantViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)
    private lateinit var resturantViewModel: RestaurantViewModel
    private lateinit var context: Context
    private val menu = "menus.json"
    private val restaurant = "resturants.json"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_splash)
        context = this@SplashActivity

        resturantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        resturantViewModel.getAllDetails()?.observe(this, Observer {
            if (it.isEmpty()) {
                insertJsonData()
            }
        })


        activityScope.launch {
            delay(2000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun insertJsonData() {

        CoroutineScope(Dispatchers.IO).launch {
            val menuDetails = application.assets.open(menu).bufferedReader().use {
                it.readText()
            }

            val resturantsDeatils = application.assets.open(restaurant).bufferedReader().use {
                it.readText()
            }

            Gson().fromJson<MenuData>(menuDetails, MenuData::class.java).also {
                resturantViewModel.insertData(it)
            }
            Gson().fromJson<Rstrnts>(resturantsDeatils, Rstrnts::class.java).also {
                resturantViewModel.insertData(it)
            }
        }

    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
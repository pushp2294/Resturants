package com.pushp.resturants.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pushp.resturants.adapter.CustomRecyclerAdapter
import com.pushp.resturants.R
import com.pushp.resturants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_res.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var context: Context
    private lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_res)
        context = this@MainActivity

        initView()

        initListener()
    }

    private fun initListener() {

        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                fetchData(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun initView() {
        val rvRecyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        restaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        rvRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CustomRecyclerAdapter(listOf())
        rvRecyclerView.adapter = adapter
    }

    fun fetchData(text: String) {

        restaurantViewModel.getRestaurantsDetails(text)?.observe(this, Observer {
            adapter.updateList(it)
        })
    }
}
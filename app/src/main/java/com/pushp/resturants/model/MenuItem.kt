package com.pushp.resturants.model

data class MenuItem(
    val description: String,
    val id: String,
    val images: List<Any>,
    val name: String,
    val price: String
)
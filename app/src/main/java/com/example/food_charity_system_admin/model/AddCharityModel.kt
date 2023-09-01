package com.example.food_charity_system_admin.model

data class AddCharityModel(
    val charityName: String? = "",
    val charityDes: String? = "",
    val charityPrice: String? = "",
    val charityId: String? = "",
    val charityCoverImg: String? = "",
    val charityImges1: String,
    val charityImges: ArrayList<String>
)

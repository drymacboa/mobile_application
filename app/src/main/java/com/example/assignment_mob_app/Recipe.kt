package com.example.assignment_mob_app

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,  // Resource ID for the image
    var isLiked: Boolean = false
)

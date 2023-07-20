package com.example.mypictures.navigation

sealed class Screens(val route: String) {

    object PhotoList : Screens("photo_list")

    object PhotoDetail : Screens("photo_detail?photoUrl={photoUrl}")

}


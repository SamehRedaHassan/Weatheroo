package com.iti.java.weatheroo.favourite.view

import com.iti.java.weatheroo.model.FavouriteLocation

interface NavigationDelegate {
    fun navigateToHome()
    fun deleteFav(location: FavouriteLocation)
}
package com.iti.java.weatheroo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class FavouriteLocation (
    var latitude  : Double,
    var longitude : Double,
    var name      : String,
    var isHome    : Boolean
    ){
    @PrimaryKey
    @NonNull
    var id : UUID = UUID.randomUUID()
}
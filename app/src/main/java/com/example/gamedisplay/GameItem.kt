package com.example.gamedisplay

import com.google.gson.annotations.SerializedName

data class GameItem (
    var title: String = "",
    @SerializedName("short_description") var description: String = "",
    var id: String = "",
    @SerializedName("thumbnail") var imageUrl: String = "",
    @SerializedName("game_url") var siteUrl: String = ""
)
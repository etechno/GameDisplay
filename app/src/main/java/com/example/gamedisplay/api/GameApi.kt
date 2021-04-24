package com.example.gamedisplay.api

import com.example.gamedisplay.GameItem
import retrofit2.Call
import retrofit2.http.GET

interface GameApi {
    @GET("api/games"
    )
    fun fetchGames(): Call<List<GameItem>>

}
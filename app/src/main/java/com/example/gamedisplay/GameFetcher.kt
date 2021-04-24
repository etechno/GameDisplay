package com.example.gamedisplay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamedisplay.api.GameApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class GameFetcher {

    private val gameApi: GameApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/")
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gameApi = retrofit.create(GameApi::class.java)
    }

    fun fetchGames(): LiveData<List<GameItem>> {
        val gameRequest: Call<List<GameItem>> = gameApi.fetchGames()
        val responseLiveData: MutableLiveData<List<GameItem>> = MutableLiveData()

        gameRequest.enqueue(object : Callback<List<GameItem>> {
            override fun onFailure(call: Call<List<GameItem>>, t: Throwable) {
                Log.e("funny", "Failed to fetch games", t)
            }

            override fun onResponse(call: Call<List<GameItem>>, response: Response<List<GameItem>>) {
                Log.d("funny", "hi")
                /*val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var galleryItems: List<GalleryItem> = photoResponse?.galleryItems ?: mutableListOf()
                */
                var gameItems: List<GameItem> = response.body() ?: mutableListOf()
                //Log.d("funny", "hi" + gameItems)
                /*
                galleryItems = galleryItems.filterNot {it.url.isBlank() }
                */
                responseLiveData.value = gameItems

            }

        })
        return responseLiveData

    }
}
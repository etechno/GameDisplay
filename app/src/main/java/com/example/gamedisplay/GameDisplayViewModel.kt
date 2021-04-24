package com.example.gamedisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class GameDisplayViewModel: ViewModel() {
    val gameItemLiveData: LiveData<List<GameItem>>

    init {
        gameItemLiveData = GameFetcher().fetchGames()
    }
}
package com.example.grcki_kino.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grcki_kino.api.ApiService
import com.example.grcki_kino.data.RoundDataClass
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RoundViewModel(private val apiService: ApiService) : ViewModel() {

    private val _roundsData = MutableLiveData<List<RoundDataClass>>()
    val roundsData: LiveData<List<RoundDataClass>> get() = _roundsData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRounds(gameId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getRounds(gameId)
                _roundsData.postValue(response)
                Log.d("RoundViewModel", "Fetched new rounds data: $response")
            } catch (e: IOException) {
                _error.value = "Network Error"
                Log.e("RoundViewModel", "Network Error: ${e.message}")
            } catch (e: HttpException) {
                _error.value = "HTTP Error: ${e.code()}"
                Log.e("RoundViewModel", "HTTP Error: ${e.code()}")
            } catch (e: JsonSyntaxException) {
                _error.value = "Data Parsing Error: ${e.message}"
                Log.e("RoundViewModel", "Data Parsing Error: ${e.message}")
            }
        }
    }

    private val _roundData = MutableLiveData<RoundDataClass>()
    val roundData: LiveData<RoundDataClass> get() = _roundData

    fun fetchRound(gameId: Int, drawId: Int) {
        viewModelScope.launch {
            try {
                // Call the updated API method
                val response = apiService.getRound(gameId, drawId)
                _roundData.postValue(response)
            } catch (e: IOException) {
                _error.value = "Network Error"
            } catch (e: HttpException) {
                _error.value = "HTTP Error: ${e.code()}"
            } catch (e: JsonSyntaxException) {
                _error.value = "Data Parsing Error: ${e.message}"
            }
        }
    }

}
package com.example.grcki_kino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grcki_kino.api.ApiService

class RoundViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoundViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
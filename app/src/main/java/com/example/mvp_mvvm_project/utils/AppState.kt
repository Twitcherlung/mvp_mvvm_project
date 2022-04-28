package com.example.mvp_mvvm_project.utils

import com.example.mvp_mvvm_project.domain.entities.UserProfile

sealed class AppState {
    data class Success(val account: UserProfile) : AppState()
    data class Error(val error: Exception) : AppState()
    object Loading : AppState()
}
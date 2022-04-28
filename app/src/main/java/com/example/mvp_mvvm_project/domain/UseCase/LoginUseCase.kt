package com.example.mvp_mvvm_project.domain.UseCase

import androidx.annotation.MainThread
import com.example.mvp_mvvm_project.domain.entities.UserProfile


interface LoginUseCase {
    fun login(
        login: String,
        password: String,
        @MainThread callback: CallbackData<UserProfile>
    )
}
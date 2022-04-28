package com.example.mvp_mvvm_project.domain

import androidx.annotation.WorkerThread
import com.example.mvp_mvvm_project.domain.entities.UserProfile


interface LoginApi {
    @WorkerThread
    fun login(login: String, password: String): UserProfile

    @WorkerThread
    fun register(login: String, password: String, email: String): UserProfile

    @WorkerThread
    fun forgotPassword(email: String): UserProfile
}
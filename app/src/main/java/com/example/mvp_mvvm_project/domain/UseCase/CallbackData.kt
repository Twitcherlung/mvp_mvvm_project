package com.example.mvp_mvvm_project.domain.UseCase

import com.example.mvp_mvvm_project.domain.entities.UserProfile

interface CallbackData<T> {

    fun onSuccess(result: UserProfile)

    fun onError(error: Exception)

}
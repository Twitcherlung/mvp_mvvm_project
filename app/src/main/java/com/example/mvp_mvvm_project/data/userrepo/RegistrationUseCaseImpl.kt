package com.example.mvp_mvvm_project.data.userrepo

import android.os.Handler
import androidx.annotation.MainThread
import com.example.mvp_mvvm_project.domain.LoginApi
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.UseCase.RegistrationUseCase

class RegistrationUseCaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : RegistrationUseCase {
    override fun register(
        login: String,
        password: String,
        email: String,
        @MainThread callback: CallbackData<UserProfile>
    ) {
        Thread {
            try {
                val account = api.register(login, password, email)
                uiHandler.post {
                    callback.onSuccess(account)
                }
            } catch (exc: Exception) {
                uiHandler.post { callback.onError(exc) }
            }
        }.start()
    }
}
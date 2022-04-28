package com.example.mvp_mvvm_project.data.userrepo

import android.os.Handler
import androidx.annotation.MainThread
import com.example.mvp_mvvm_project.domain.LoginApi
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.UseCase.LoginUseCase

class LoginUseCaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : LoginUseCase {
    override fun login(
        login: String,
        password: String,
        @MainThread callback: CallbackData<UserProfile>
    ) {
        Thread {
            try {
                val account = api.login(login, password)
                uiHandler.post {
                    callback.onSuccess(account)
                }
            } catch (exc: Exception) {
                uiHandler.post { callback.onError(exc) }
            }
        }.start()
    }
}

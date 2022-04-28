package com.example.mvp_mvvm_project.ui.login

import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.UseCase.LoginUseCase
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.Publisher

class LoginViewModel(
    private val loginDataSource: LoginUseCase
) : LoginContract.ViewModel {

    private val liveData: Publisher<AppState> = Publisher()

    override fun getLiveData(): Publisher<AppState> = liveData

    override fun onLogin(login: String, password: String) {
        liveData.postValue(AppState.Loading)
        loginDataSource.login(login, password, object : CallbackData<UserProfile> {
            override fun onSuccess(result: UserProfile) {
                liveData.postValue(AppState.Success(result))
            }
            override fun onError(error: Exception) {
                liveData.postValue(AppState.Error(error))
            }
        })
    }
}
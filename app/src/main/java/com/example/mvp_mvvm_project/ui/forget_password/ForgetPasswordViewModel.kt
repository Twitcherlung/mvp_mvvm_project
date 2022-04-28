package com.example.mvp_mvvm_project.ui.forget_password

import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.ForgetPasswordUseCase
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.Publisher


class ForgetPasswordViewModel(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
) : ForgetPasswordContract.ViewModel {
    private val liveData: Publisher<AppState> = Publisher()
    override fun getLiveData(): Publisher<AppState> = liveData

    override fun findAccount(email: String) {
        liveData.postValue(AppState.Loading)
        forgetPasswordUseCase.forgetPassword(email, object : CallbackData<UserProfile> {
            override fun onSuccess(result: UserProfile) {
                liveData.postValue(AppState.Success(result))
            }

            override fun onError(error: Exception) {
                liveData.postValue(AppState.Error(error))
            }
        })
    }
}
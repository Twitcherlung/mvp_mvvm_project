package com.example.mvp_mvvm_project.ui.registration

import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.RegistrationUseCase
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.Publisher


class RegistrationViewModel(
    private val registrationDataSource: RegistrationUseCase
) : RegistrationContract.ViewModel {

    private val liveData: Publisher<AppState> = Publisher()

    override fun getLiveData(): Publisher<AppState> = liveData

    override fun onRegistration(login: String, password: String, email: String) {
        liveData.postValue(AppState.Loading)
        registrationDataSource.register(login, password, email, object : CallbackData<UserProfile> {
            override fun onSuccess(result: UserProfile) {
                liveData.postValue(AppState.Success(result))
            }
            override fun onError(error: Exception) {
                liveData.postValue(AppState.Error(error))
            }
        })
    }
}
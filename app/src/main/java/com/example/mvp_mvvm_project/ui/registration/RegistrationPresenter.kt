package com.example.mvp_mvvm_project.ui.registration

import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.RegistrationUseCase


class RegistrationPresenter(
    private val registrationUseCase: RegistrationUseCase
) : RegistrationContract.RegistrationPresenterInterface {

    private var isSuccess: Boolean = false
    private var view: RegistrationContract.RegistrationViewInterface? = null

    override fun onAttachView(view: RegistrationContract.RegistrationViewInterface) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        }
    }

    override fun onRegistration(login: String, password: String, email: String) {
        view?.showProgress()
        registrationUseCase.register(login, password, email, object : CallbackData<UserProfile> {
            override fun onSuccess(result: UserProfile) {
                view?.hideProgress()
                view?.loadAccountData(result)
                view?.setSuccess()
                isSuccess = true
            }
            override fun onError(error: Exception) {
                view?.hideProgress()
                view?.showError(error)
                isSuccess = false
            }
        })
    }

    override fun onDetach() {
        this.view = null
    }
}
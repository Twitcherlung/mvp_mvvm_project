package com.example.mvp_mvvm_project.ui.forget_password

import com.example.mvp_mvvm_project.domain.UseCase.CallbackData
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.domain.UseCase.ForgetPasswordUseCase


class ForgetPasswordPresenter(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
) : ForgetPasswordContract.ForgetPasswordPresenterInterface {

    private var isSuccess: Boolean = false
    private var view: ForgetPasswordContract.ForgetPasswordViewInterface? = null

    override fun onAttachView(view: ForgetPasswordContract.ForgetPasswordViewInterface) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        }
    }

    override fun findAccount(email: String) {
        view?.showProgress()
        forgetPasswordUseCase.forgetPassword(email, object : CallbackData<UserProfile> {
            override fun onSuccess(result: UserProfile) {
                view?.hideProgress()
                view?.forgetPasswordData(result)
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
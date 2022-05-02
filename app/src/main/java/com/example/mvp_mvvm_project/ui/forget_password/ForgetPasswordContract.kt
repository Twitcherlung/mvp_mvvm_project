package com.example.mvp_mvvm_project.ui.forget_password

import com.example.mvp_mvvm_project.domain.entities.UserProfile

class ForgetPasswordContract {
    interface ForgetPasswordViewInterface {
        fun showProgress()
        fun hideProgress()
        fun setSuccess()
        fun showError(error: Exception)
        fun forgetPasswordData(account: UserProfile)
    }

    interface ForgetPasswordPresenterInterface  {
        fun onAttachView(view: ForgetPasswordViewInterface)
        fun findAccount(email: String)
        fun onDetach()
    }
}
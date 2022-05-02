package com.example.mvp_mvvm_project.ui.login

import com.example.mvp_mvvm_project.domain.entities.UserProfile


class LoginContract {
    interface LoginViewInterface {
        fun showProgress()
        fun hideProgress()
        fun setSuccess()
        fun showError(error: Exception)
        fun loadAccountData(account: UserProfile)
    }

    interface LoginPresenterInterface  {
        fun onAttachView(view: LoginViewInterface)
        fun onLogin(login: String, password: String)
        fun onDetach()
    }
}

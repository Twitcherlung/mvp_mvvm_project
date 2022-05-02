package com.example.mvp_mvvm_project.ui.registration

import com.example.mvp_mvvm_project.domain.entities.UserProfile


class RegistrationContract {
    interface RegistrationViewInterface {
        fun showProgress()
        fun hideProgress()
        fun setSuccess()
        fun showError(error: Exception)
        fun loadAccountData(account: UserProfile)
    }

    interface RegistrationPresenterInterface  {
        fun onAttachView(view: RegistrationViewInterface)
        fun onRegistration(login: String, password: String, email: String)
        fun onDetach()
    }
}
package com.example.mvp_mvvm_project.ui.registration

import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.Publisher


class RegistrationContract {
    interface ViewModel  {
        fun getLiveData() : Publisher<AppState>
        fun onRegistration(login: String, password: String, email: String)
    }
}
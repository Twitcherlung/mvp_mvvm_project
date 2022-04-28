package com.example.mvp_mvvm_project.ui.forget_password

import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.Publisher

class ForgetPasswordContract {
    interface ViewModel {
        fun getLiveData(): Publisher<AppState>
        fun findAccount(email: String)

    }
}
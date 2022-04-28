package com.example.mvp_mvvm_project

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.mvp_mvvm_project.data.database.AccountsDao
import com.example.mvp_mvvm_project.data.database.AccountsDataBase
import com.example.mvp_mvvm_project.data.userrepo.ForgetPasswordUseCaseImpl
import com.example.mvp_mvvm_project.data.userrepo.LoginUseCaseImpl
import com.example.mvp_mvvm_project.data.userrepo.RegistrationUseCaseImpl
import com.example.mvp_mvvm_project.data.userrepo.RoomLoginApiImpl
import com.example.mvp_mvvm_project.domain.LoginApi
import com.example.mvp_mvvm_project.domain.UseCase.ForgetPasswordUseCase
import com.example.mvp_mvvm_project.domain.UseCase.LoginUseCase
import com.example.mvp_mvvm_project.domain.UseCase.RegistrationUseCase

class App : Application() {
    private val loginApi: LoginApi by lazy { RoomLoginApiImpl(getAccountDao()) }

    val loginUseCase: LoginUseCase by lazy {
        LoginUseCaseImpl(app.loginApi , Handler(Looper.getMainLooper()))
    }
    val registrationUseCase: RegistrationUseCase by lazy {
        RegistrationUseCaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }
    val forgetPasswordUseCase: ForgetPasswordUseCase by lazy {
        ForgetPasswordUseCaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(this, AccountsDataBase::class.java, "Accounts.database")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        private var db: AccountsDataBase? = null

        fun getAccountDao(): AccountsDao {
            return db!!.accountDao()
        }
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }
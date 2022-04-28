package com.example.mvp_mvvm_project.data.userrepo

import com.example.mvp_mvvm_project.data.database.*
import com.example.mvp_mvvm_project.domain.LoginApi
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.data.database.convertAccountToEntities

class RoomLoginApiImpl(private val localDataSource: AccountsDao) : LoginApi {

    fun getAllAccounts(): List<AccountEntity> = localDataSource.getAllAccountData()

    private fun checkData(login: String?, password: String?, email: String?){
        if (login != null && login.isEmpty()){
            throw LoginException()
        }

        if (password != null && password.isEmpty()){
            throw PasswordException()
        }

        if (email != null && email.isEmpty()){
            throw EmailException()
        }
    }

    override fun login(login: String, password: String) : UserProfile {
        checkData(login, password, null)
        val accountsList = getAllAccounts()
        for (account in accountsList) {
            if (account.login == login && account.password == password) {
                return convertAccountToEntities(account)
            }
        }
        throw SignInException()
    }

    override fun register(login: String, password: String, email: String) : UserProfile {
        checkData(login, password, null)
        val accountsList = getAllAccounts()
        for (account in accountsList) {
            if(account.login == login && account.email == email) {
                throw RegistrationException()
            }
        }
        val newAccount = AccountEntity(uid=null, login = login, password = password, email = email)
        localDataSource.registration(newAccount)
        return convertAccountToEntities(newAccount)
    }

    override fun forgotPassword(email: String) : UserProfile {
        checkData(null, null, email)
        val accountsList = getAllAccounts()
        for (account in accountsList) {
            if (account.email == email) {
                return convertAccountToEntities(account)
            }
        }
        throw ForgetPasswordException()
    }
}
package com.example.mvp_mvvm_project.domain.UseCase

import com.example.mvp_mvvm_project.data.database.AccountEntity
import com.example.mvp_mvvm_project.domain.entities.UserProfile

fun convertAccountToEntities(account: AccountEntity): UserProfile {
    return UserProfile(
        id = account.uid,
        login = account.login,
        email = account.email
    )
}
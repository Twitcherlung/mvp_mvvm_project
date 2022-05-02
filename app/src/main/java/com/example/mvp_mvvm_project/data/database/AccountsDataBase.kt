package com.example.mvp_mvvm_project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AccountEntity::class], version = 2)
abstract class AccountsDataBase : RoomDatabase() {
    abstract fun accountDao(): AccountsDao
}
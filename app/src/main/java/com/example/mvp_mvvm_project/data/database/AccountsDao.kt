package com.example.mvp_mvvm_project.data.database

import androidx.room.*

@Dao
interface AccountsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registration(entity: AccountEntity)

    @Query("SELECT * FROM AccountEntity")
    fun getAllAccountData(): List<AccountEntity>
}
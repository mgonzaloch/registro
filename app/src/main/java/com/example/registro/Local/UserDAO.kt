package com.example.registro.Local

import androidx.room.*
import com.example.registro.Model.User
import io.reactivex.Flowable


@Dao
interface UserDAO {

    @get:Query("select * from users")
    val allUser: Flowable<List<User>>
    @Query("select * from users where id=:userId")
    fun getUserById(userId:Int): Flowable<User>
    @Insert
    fun insertUser(vararg users: User)
    @Update
    fun updateUser(vararg users: User)
    @Delete
    fun deleteUser(user: User)
    @Query("delete from users")
    fun deleteAllUsers()


}
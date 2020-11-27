package com.example.registro.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity (tableName = "users")
class User {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "email")
    var email: String? = null

    constructor(){}

    override fun toString(): String {
        return StringBuilder(name!!)
                .append("\n")
                .append(email)
                .toString()
    }
}
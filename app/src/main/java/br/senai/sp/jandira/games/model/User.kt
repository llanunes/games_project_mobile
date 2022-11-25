package br.senai.sp.jandira.games.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User (
    val profilePicture: ByteArray,
    val userName: String,
    val email: String,
    val password: String,
    val city: String,
    val level: NiveisEnum,
    val genre: Char,
    @ColumnInfo(name = "birthday")
    val birthday: String,
    @Embedded
    val console: Console
) {
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0
}
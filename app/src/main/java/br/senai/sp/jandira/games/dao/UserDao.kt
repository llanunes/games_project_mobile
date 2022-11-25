package br.senai.sp.jandira.games.dao

import androidx.room.*
import br.senai.sp.jandira.games.model.UserGames
import br.senai.sp.jandira.games.model.User

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT *  FROM tbl_user WHERE userId = :id")
    fun getUserGames(id: Int): List<UserGames>

    @Query("SELECT * FROM tbl_user ORDER BY userName ASC")
    fun getAll(): List<User>

    @Query("SELECT * FROM tbl_user WHERE userId = :id")
    fun getUserById(id: Int): User

    @Query("SELECT * FROM tbl_user where email = :email")
    fun getUserByEmail(email: String): User

    @Update
    fun update(user: User): Int

    @Delete
    fun delete(user: User): Int

    @Insert
    fun save(user: User): Long
}
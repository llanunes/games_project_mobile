package br.senai.sp.jandira.games.dao

import androidx.room.*
import br.senai.sp.jandira.games.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM tbl_game ORDER BY gameTitle ASC")
    fun getAll(): List<Game>

    @Query("SELECT * FROM tbl_game WHERE gameId = :id")
    fun getGameById(id: Int): Game

    @Update
    fun update(game: Game): Int

    @Delete
    fun delete(game: Game): Int

    @Insert
    fun save(game: Game): Long
}
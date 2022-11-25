package br.senai.sp.jandira.games.dao

import androidx.room.*
import br.senai.sp.jandira.games.model.Console

@Dao
interface ConsoleDao {
    @Query("SELECT * FROM tbl_console ORDER BY consoleName ASC")
    fun getAll(): List<Console>

    @Query("SELECT * FROM tbl_console WHERE consoleId = :id")
    fun getConsoleById(id: Int): Console

    @Query("SELECT * FROM tbl_console WHERE consoleName = :name")
    fun getConsoleByName(name: String): Console
    
    @Update
    fun update(console: Console): Int

    @Delete
    fun delete(console: Console): Int

    @Insert
    fun save(console: Console): Long
}
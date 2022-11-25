package br.senai.sp.jandira.games.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.senai.sp.jandira.games.dao.ConsoleDao
import br.senai.sp.jandira.games.dao.GameDao
import br.senai.sp.jandira.games.dao.UserDao
import br.senai.sp.jandira.games.model.Console
import br.senai.sp.jandira.games.model.Game
import br.senai.sp.jandira.games.model.User
import br.senai.sp.jandira.games.repository.ConsoleRepository


@Database(entities = [Console::class, User::class, Game::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun consoleDao(): ConsoleDao

    companion object {
        private lateinit var instance: MainDb

        private fun initConsolesRegister(context: Context) {
            ConsoleRepository(context).save(Console("PS5", "Sony", "", ByteArray(0), 2021))
            ConsoleRepository(context).save(
                Console(
                    "Xbox Series X",
                    "Microsoft",
                    "",
                    ByteArray(0),
                    2021
                )
            )

        }

        fun getDatabase(context: Context): MainDb {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(context, MainDb::class.java, "db_app_game")
                    .allowMainThreadQueries().build()
                if (ConsoleRepository(context).getAll().isEmpty())
                    initConsolesRegister(context)
            }
            return instance
        }
    }
}
package br.senai.sp.jandira.games.repository

import android.content.Context
import br.senai.sp.jandira.games.database.MainDb
import br.senai.sp.jandira.games.model.Console

class ConsoleRepository(context: Context) {
    private val db = MainDb.getDatabase(context).consoleDao()

    fun getAll(): List<Console> {
        return db.getAll()
    }

    fun getConsoleByName(name: String): Console {
        return db.getConsoleByName(name)
    }

    fun save(console: Console): Long {
        return db.save(console)
    }
    fun update(console: Console): Int {
        return db.update(console)
    }
    fun delete(console: Console): Int {
        return db.delete(console)
    }
}
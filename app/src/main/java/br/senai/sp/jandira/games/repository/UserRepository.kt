package br.senai.sp.jandira.games.repository

import android.content.Context
import br.senai.sp.jandira.games.database.MainDb
import br.senai.sp.jandira.games.model.User
import br.senai.sp.jandira.games.model.UserGames

class UserRepository(context: Context) {
    private val db = MainDb.getDatabase(context).userDao()
    fun getAll(): List<User> {
        return db.getAll()
    }

    fun getUserByEmail(email: String): User {
        return db.getUserByEmail(email)
    }

    fun getUserGames(id:Int): List<UserGames> {
        return db.getUserGames(id)
    }

    fun getUserById(id: Int): User {
        return db.getUserById(id)
    }
    fun save(user: User): Long {
        return db.save(user)
    }
    fun update(user: User): Int {
        return db.update(user)
    }
    fun delete(user: User): Int {
        return db.delete(user)
    }
}
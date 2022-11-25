package br.senai.sp.jandira.games.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_game")
data class Game(
    val gamePicture: ByteArray,
    val gameTitle: String,
    val gameDescription: String,
    val gameStudio: String,
    val gameReleasedYear: Int,
    val finished: Boolean,
    // user id
    val ownerId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var gameId: Int = 0
}
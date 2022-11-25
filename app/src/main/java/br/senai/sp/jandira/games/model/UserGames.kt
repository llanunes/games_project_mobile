package br.senai.sp.jandira.games.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserGames(
    @Embedded
    val owner: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val listOfGames: List<Game>
)

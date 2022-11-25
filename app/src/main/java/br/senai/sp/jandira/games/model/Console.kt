package br.senai.sp.jandira.games.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "tbl_console")
data class Console(
    val consoleName: String,
    val producer: String,
    val description: String,
    val console_picture: ByteArray,
    @ColumnInfo(name = "released_year")
    var released_console_year: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    var consoleId: Int = 0
}
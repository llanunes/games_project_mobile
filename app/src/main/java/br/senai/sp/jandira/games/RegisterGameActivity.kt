package br.senai.sp.jandira.games

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.games.databinding.ActivityRegisterGameBinding
import br.senai.sp.jandira.games.model.Game
import br.senai.sp.jandira.games.repository.GameRepository
import br.senai.sp.jandira.games.repository.UserRepository
import br.senai.sp.jandira.games.utils.getBitmapFromUri
import br.senai.sp.jandira.games.utils.getByteArrayFromBitmap


// TODO FINISH THE REGISTER ACTIVITY AND POPULATE FOR THE FIRST TIME THE DB TO TEST AND FIX THE ISSUES
class RegisterGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterGameBinding
    private var gamePictureBitmap: Bitmap? = null

    // default value
    private var userId: Int = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title =  getString(R.string.actionBar_game_register_title)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(this, R.color.blue_dark)
            )
        )

        userId = intent.getIntExtra("user_id", -1)

        binding.gameUploadImageButton.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun validateForm(): Boolean {
        var validated = true
        val inputs = listOf(
            binding.gameDescEditText,
            binding.gameNameEditText,
            binding.gameStudioEditText,
            binding.gameYearReleasedEditTextNumber
        )

        inputs.forEach { editText ->
            if (editText.text.isEmpty()) {
                editText.error = "Campo Requerido"
                validated = false
            }
        }

        if (gamePictureBitmap == null) {
            Toast.makeText(this, "Selecione uma foto de capa para o game!", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (binding.gameFinishedradioGroup.checkedRadioButtonId.toString().isEmpty()) {
            Toast.makeText(this, "Preencha o campo de jogo finalizado ou não!", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return validated
    }

    private fun getForm(): Game {
        val (desc, name, studio, year) = listOf(
            binding.gameDescEditText.text.toString(),
            binding.gameNameEditText.text.toString(),
            binding.gameStudioEditText.text.toString(),
            binding.gameYearReleasedEditTextNumber.text.toString()
        )

        val inputRadio = binding.gameFinishedradioGroup.checkedRadioButtonId
        val inputValue = findViewById<RadioButton>(inputRadio).text.toString()[0]


        var finished = false
        if(inputValue == 'y' || inputValue == 's') finished = true

        val gamePicture = getByteArrayFromBitmap(gamePictureBitmap)

        return Game(gamePicture, name, desc, studio, year.toInt(), finished, userId)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_register, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (validateForm()) {
            GameRepository(this).save(getForm())
            finish()
            return true
        }
        return false
    }

    // PICK GAME IMAGE
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            gamePictureBitmap = getBitmapFromUri(data?.data, this)
            binding.gameUploadImageButton.setImageBitmap(gamePictureBitmap)
        }
    }

}
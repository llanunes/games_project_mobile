package br.senai.sp.jandira.games

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.games.databinding.ActivitySingupBinding
import br.senai.sp.jandira.games.model.NiveisEnum
import br.senai.sp.jandira.games.model.User
import br.senai.sp.jandira.games.repository.ConsoleRepository
import br.senai.sp.jandira.games.repository.UserRepository
import br.senai.sp.jandira.games.utils.getBitmapFromUri
import br.senai.sp.jandira.games.utils.getByteArrayFromBitmap

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var profilePicture: Bitmap
    private val IMAGE_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title =  getString(R.string.actionBar_game_register_user)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(this, R.color.blue_dark)
            )
        )

        spinnerAdapter()
        sliderListen()

        binding.selectedImageView.visibility = View.INVISIBLE

        binding.profilePictureImageView.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun validateForm(): Boolean {
        var validated = true

        val textsInputs = listOf(
            binding.emailAddressEditText,
            binding.nameEditText,
            binding.cityEditText,
            binding.birthdayEditText,
            binding.passwordFieldEditText
        )
        textsInputs.forEach { editText ->
            if (editText.text.isEmpty()) {
                editText.error = "Campo Requirido"
                validated = false
            }
        }

        if (binding.genreRadioGroup.checkedRadioButtonId.toString().isEmpty()) {
            Toast.makeText(this, "Preencha o campo de genero Porfavor!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (getSpinnerValue().isEmpty()) {
            Toast.makeText(this, "Selecione um Console!", Toast.LENGTH_SHORT).show()
            return false
        }

        return validated
    }

    private fun getForm(): User {
        val (email, name, city, birthday, password) = listOf(
            binding.emailAddressEditText.text.toString(),
            binding.nameEditText.text.toString(),
            binding.cityEditText.text.toString(),
            binding.birthdayEditText.text.toString(),
            binding.passwordFieldEditText.text.toString()
        )
        val level = getSliderLevel(binding.slider.value.toInt())
        val console = ConsoleRepository(this).getConsoleByName(getSpinnerValue())
        val picture = getByteArrayFromBitmap(profilePicture)
        // genre
        val inputRadio = binding.genreRadioGroup.checkedRadioButtonId
        val genre = findViewById<RadioButton>(inputRadio).text.toString()[0]

        return User(
            profilePicture = picture,
            userName = name,
            email = email,
            password = password,
            birthday = birthday,
            city = city,
            console = console,
            level = level,
            genre = genre
        )
    }

    private fun spinnerAdapter() {
        val consoles = ConsoleRepository(this).getAll().map { e -> e.consoleName }
        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, consoles)
    }

    private fun getSpinnerValue(): String {
        return binding.spinner.selectedItem.toString()
    }

    private fun sliderListen() {
        binding.slider.addOnChangeListener { slider, value, fromUser ->
            binding.levelTextView.text = getSliderLevel(binding.slider.value.toInt()).toString()
        }
    }

    private fun getSliderLevel(pos: Int): NiveisEnum {
        if (pos <= 40) return NiveisEnum.INICIANTE
        if (pos in 41..60) return NiveisEnum.BASICO
        if (pos in 61..81) return NiveisEnum.CASUAL
        return NiveisEnum.AVANCADO
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_register, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (validateForm()) {
            UserRepository(this).save(getForm())
            finish()
            return true
        }
        return false
    }

    // PICK IMAGE PROFILE
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            this.profilePicture = getBitmapFromUri(data?.data, this)

            binding.selectedImageView.visibility = View.VISIBLE
            binding.selectedImageView.setImageBitmap(profilePicture)
        }
    }

}
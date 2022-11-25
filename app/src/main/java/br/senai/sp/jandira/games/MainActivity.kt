package br.senai.sp.jandira.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.games.databinding.ActivityMainBinding
import br.senai.sp.jandira.games.repository.UserRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.loginButton.setOnClickListener {
            if(!validateForm()) {
                Toast.makeText(this, "Erro no Login Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            else if(!checkWithUserExists(binding.editTextEmail.text.toString())) {
                Toast.makeText(this, "Email não encontrado faço um cadastro!", Toast.LENGTH_SHORT).show()
            }
            else {
                val openGamesListActivity = Intent(this, GamesListActivity::class.java)

                // insert the user id for the home activity
                openGamesListActivity.putExtra("user_id", UserRepository(this).getUserByEmail(binding.editTextEmail.text.toString()).userId)

                startActivity(openGamesListActivity)
            }
        }

        binding.registerNewAccount.setOnClickListener {
            val openSingUpActivity = Intent(this, SingUpActivity::class.java)
            startActivity(openSingUpActivity)
        }
    }

    private fun validateForm(): Boolean {
        if (binding.editTextEmail.text.isEmpty()) {
            binding.editTextEmail.error = "Campo Requirido"
            return false
        }
        if (binding.editTextPassword.text.isEmpty()) {
            binding.editTextPassword.error = "Campo Requirido"
            return false
        }
        return true
    }

    private fun checkWithUserExists(email: String): Boolean {
        if (UserRepository(this).getUserByEmail(email) == null) {
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "${UserRepository(this).getAll()}", Toast.LENGTH_SHORT).show()
    }


}
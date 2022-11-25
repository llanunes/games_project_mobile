package br.senai.sp.jandira.games

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.games.adapter.GamesAdapter
import br.senai.sp.jandira.games.databinding.ActivityGamesListBinding
import br.senai.sp.jandira.games.model.Game
import br.senai.sp.jandira.games.model.User
import br.senai.sp.jandira.games.repository.UserRepository
import br.senai.sp.jandira.games.utils.getBitmapFromByteArray
import java.time.Year


class GamesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesListBinding
    lateinit var rvGames: RecyclerView
    lateinit var adapterGames: GamesAdapter

    private var userId: Int = 0
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title =  getString(R.string.actionBar_game_list_title)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(this, R.color.blue_dark)
            )
        )

        supportActionBar?.elevation = 0F

        // get the user id
        userId = intent.getIntExtra("user_id", -1)
        user = UserRepository(this).getUserById(userId)

        // update the user
        updateUserInfo()

        rvGames = binding.gameListRv
        rvGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterGames = GamesAdapter(this)
        adapterGames.updateGamesList(updateGameList(userId))


        updateGamesStatus(UserRepository(this).getUserGames(userId)[0].listOfGames)

        rvGames.adapter = adapterGames
    }

    private fun updateGameList(userId: Int): List<Game> {
        return UserRepository(this).getUserGames(userId)[0].listOfGames
    }

    private fun updateUserInfo() {
        binding.userNameTextView.text = user.userName
        binding.userEmailTextView.text = user.email
        binding.userLevelTextView.text = user.level.toString()
        binding.userPictureImageView.setImageBitmap(getBitmapFromByteArray(user.profilePicture))

        val age =
            Year.now().value - user.birthday.substring(user.birthday.length - 4).toInt()


        binding.userAgeTextView.text = age.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_add_game, menu)
        return true
    }

    private fun updateGamesStatus(games: List<Game>) {
        var completedGames = 0
        var unfinishedGames = 0
        games.forEach { game ->
            if (game.finished) {
                completedGames++
            } else {
                unfinishedGames++
            }
        }
        binding.countCompletedGamesTextView.text = completedGames.toString()
        binding.countPlayingGamesTextView.text = unfinishedGames.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val openRegisterGameActivity = Intent(this, RegisterGameActivity::class.java)
        openRegisterGameActivity.putExtra("user_id", userId)
        startActivity(openRegisterGameActivity)
        return true
    }

    override fun onResume() {
        super.onResume()

        adapterGames.updateGamesList(updateGameList(userId))

        updateUserInfo()
        updateGamesStatus(UserRepository(this).getUserGames(userId)[0].listOfGames)
    }
}
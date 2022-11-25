package br.senai.sp.jandira.games.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.games.R
import br.senai.sp.jandira.games.model.Game
import br.senai.sp.jandira.games.utils.getBitmapFromByteArray

class GamesAdapter(val context: Context): RecyclerView.Adapter<GamesAdapter.HolderGames>() {

    private var gamesList = listOf<Game>()

    fun updateGamesList(games: List<Game>) {
        this.gamesList = games
        notifyDataSetChanged()
    }

    class HolderGames(val view: View): RecyclerView.ViewHolder(view) {

        val companyNameHolder = view.findViewById<TextView>(R.id.studio_name_text_view)
        val gameNameHolder = view.findViewById<TextView>(R.id.game_title_text_view)
        val descriptiomHolder = view.findViewById<TextView>(R.id.game_description_text_view)
        val photoHolder = view.findViewById<ImageView>(R.id.banner_image_view)

        fun bind(games: Game) {

            companyNameHolder.text = games.gameStudio
            gameNameHolder.text = games.gameTitle
            descriptiomHolder.text = games.gameDescription
            photoHolder.setImageBitmap(getBitmapFromByteArray(games.gamePicture))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderGames {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_games, parent, false)
        return HolderGames(view)
    }

    override fun onBindViewHolder(holder: HolderGames, position: Int) {
        holder.bind(gamesList[position])
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

}
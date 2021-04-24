package com.example.gamedisplay

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.squareup.picasso.Picasso
import java.util.*

class GameDisplayFragment: Fragment() {

    interface Callbacks {
        fun onGameSelected(gameTitle: String, gameDescription: String, gameWebUrl: String, gameDisplayUrl: String)
    }

    private var callbacks: Callbacks? = null

    private lateinit var gameDisplayViewModel: GameDisplayViewModel

    private lateinit var gameRecyclerView: RecyclerView

    private var adapter: GameAdapter? = GameAdapter(emptyList())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameDisplayViewModel = ViewModelProviders.of(this).get(GameDisplayViewModel::class.java)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_display, container, false)
        gameRecyclerView = view.findViewById(R.id.game_recycler_view)
        //gameRecyclerView.layoutManager = GridLayoutManager(context, 3)
        gameRecyclerView.layoutManager = LinearLayoutManager(context)
        gameRecyclerView.adapter = adapter

        //photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        //photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        //photoRecyclerView.adapter = adapter
        /*val flickrLiveData: LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()

        flickrLiveData.observe ( this, Observer {responseString ->
            Log.d("joke", "Response from Server: $responseString")
            photoRecyclerView.adapter = PhotoAdapter(responseString)
        })*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameDisplayViewModel.gameItemLiveData.observe( viewLifecycleOwner, Observer { gameItems ->
            Log.d("joke", "Response from Server: $gameItems")
            gameRecyclerView.adapter = GameAdapter(gameItems)
        })
    }


    private inner class GameHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var game: GameItem
        private var titleTextView: TextView = itemView.findViewById(R.id.gameTitle)
        private var descriptionTextView: TextView = itemView.findViewById(R.id.gameDescription)
        //        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
        private val imageView: ImageView = itemView.findViewById(R.id.gameImageView)
        init {
            itemView.setOnClickListener {
                callbacks?.onGameSelected(game.title,game.description,game.siteUrl,game.imageUrl)

            }
        }
        fun bindImageFromUrl(gameItem: GameItem) {
            this.game = gameItem
            titleTextView.text = gameItem.title
            Picasso.get().load(gameItem.imageUrl).resize(400,400).centerCrop().into(imageView);
            descriptionTextView.text = gameItem.description
            /*imageView.setOnClickListener {
                val url = gameItem.siteUrl
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }*/
            //val imageView = itemView as ImageView
            // load remote url

            //Log.d("joke", "Should be loaded")
        }
        fun onClick(view: View){
//            val fragment = CrimeFragment.newInstance(crime.id)
//            val fm = activity?.supportFragmentManager
//            fm?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.commit()
            callbacks?.onGameSelected(game.title,game.description,game.siteUrl,game.imageUrl)
        }
    }
    private inner class GameAdapter(var gameItems: List<GameItem>): RecyclerView.Adapter<GameHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
            val view = layoutInflater.inflate(R.layout.item_display, parent, false)
            return GameHolder(view)
        }

        override fun getItemCount(): Int {
            return gameItems.size
        }

        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            val game = gameItems[position]

            holder.bindImageFromUrl(game)
        }

    }

    companion object {
        fun newInstance(): GameDisplayFragment {
            return GameDisplayFragment()
        }
    }
}
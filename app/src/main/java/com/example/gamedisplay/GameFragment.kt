package layout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.example.gamedisplay.GameItem
import com.example.gamedisplay.R
import com.squareup.picasso.Picasso
import java.util.*

private const val ARG_GAME_TITLE = "game_title"
private const val ARG_GAME_DESC = "game_description"
private const val ARG_GAME_WEB_URL ="game_web_url"
private const val ARG_GAME_DISPLAY_URL ="game_display_url"

class GameFragment : Fragment() {



    private lateinit var game: GameItem
    private lateinit var titleField: TextView
    private lateinit var descriptionField: TextView
    private lateinit var webButton: Button
    private lateinit var imageView: ImageView
    private lateinit var mp: MediaPlayer

//    private val gameDetailViewModel: GameDetailViewModel by lazy {
//        ViewModelProviders.of(this).get(GameDetailViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        game = GameItem()
        game.title = arguments?.getSerializable(ARG_GAME_TITLE) as String
        game.description = arguments?.getSerializable(ARG_GAME_DESC) as String
        game.siteUrl = arguments?.getSerializable(ARG_GAME_WEB_URL) as String
        game.imageUrl = arguments?.getSerializable(ARG_GAME_DISPLAY_URL) as String
        mp = MediaPlayer.create(context, R.raw.quack);
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        //gameDetailViewModel.loadCrime(crimeId)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        titleField = view.findViewById(R.id.gameTitle) as TextView
        descriptionField = view.findViewById(R.id.gameDescription) as TextView
        webButton = view.findViewById(R.id.webNavigate) as Button
        imageView = view.findViewById(R.id.gameImageView) as ImageView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        /*crimeDetailViewModel.crimeLiveData.observe(
                viewLifecycleOwner,
                Observer{ crime ->
                    crime?.let {
                        this.crime = crime
                        updateUI()
                    }
                })*/
    }
    private fun updateUI() {
        titleField.setText(game.title)
        descriptionField.text = game.description
        //var mp = MediaPlayer.create(context, R.raw.quack);
        webButton.setOnClickListener{
            mp.start()
            val url = game.siteUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        Picasso.get().load(game.imageUrl).resize(1000,800).centerCrop().into(imageView);
    }

    /*override fun onStart(){
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                // this space left blank
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // space also left blank
            }
        }
        titleField.addTextChangedListener(titleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener{ _, isChecked-> crime.isSolved = isChecked}
        }
    }
    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }*/


    companion object {
        fun newInstance(gameTitle: String, gameDescription: String, gameWebUrl: String, gameDisplayUrl: String): GameFragment {
            //val fragment = CrimeFragment()

            val args = Bundle().apply {
                putSerializable(ARG_GAME_TITLE, gameTitle)
                putSerializable(ARG_GAME_DESC, gameDescription)
                putSerializable(ARG_GAME_WEB_URL, gameWebUrl)
                putSerializable(ARG_GAME_DISPLAY_URL, gameDisplayUrl)
            }
            return GameFragment().apply {
                arguments = args
            }
        }
    }
}
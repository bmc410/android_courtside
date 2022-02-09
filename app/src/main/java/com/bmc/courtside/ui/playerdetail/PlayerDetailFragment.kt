package com.bmc.courtside.ui.playerdetail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.Team
import com.bmc.courtside.repos.PlayerRepo
import com.bmc.courtside.repos.TeamRepo
import com.bmc.courtside.ui.teams.TeamsFragment
import java.util.ArrayList


class PlayerDetailFragment : Fragment() {

    private var mContext: Context? = null
    companion object {
        fun newInstance() = PlayerDetailFragment()
    }

    private lateinit var tf: TeamsFragment
    private lateinit var players: ArrayList<Player>
    //var years: MutableList<String> = ArrayList()
    private val clubsArray: MutableList<String> = ArrayList()

    var _id: Number = -1
    //var teamname: String = ""
    var y: String? = ""
    var cl: Number? = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(arguments != null) {
            _id = arguments?.get("id") as Number
        }

        mContext = context as MainActivity

        if(_id != -1) {
            val p = PlayerRepo(mContext as MainActivity)
            val player = p.getPlayer(_id)
            var fn: TextView = (context as MainActivity).findViewById(R.id.txt_player_first_name) as TextView
            if (player != null) {
                fn.text = player.firstname
            }
            var ln: TextView = (context as MainActivity).findViewById(R.id.txt_player_last_name) as TextView
            if (player != null) {
                ln.text = player.lastname
            }
            var em: TextView = (context as MainActivity).findViewById(R.id.txt_player_email) as TextView
            if (player != null) {
                em.text = player.email
            }

            if (player != null) {
                (context as MainActivity).supportActionBar?.title = player.firstname
            }
            //team name

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val n = Navigation.findNavController(mContext as MainActivity, R.id.nav_host_fragment_content_main)
        return when (item.itemId) {
            R.id.action_save_player -> {
                var  fn: String = ""
                var  ln: String = ""
                var  em: String = ""

                var fnt: TextView = (context as MainActivity).findViewById(R.id.txt_player_first_name) as TextView
                    fn = fnt.text.toString()
                var lnt: TextView = (context as MainActivity).findViewById(R.id.txt_player_last_name) as TextView
                    ln=   lnt.text.toString()
                var emt: TextView = (context as MainActivity).findViewById(R.id.txt_player_email) as TextView
                    em=   emt.text.toString()

                val p = PlayerRepo(mContext as MainActivity)
                //val newplayer = Player(null,fn,ln,em)
                val newplayer = Player()
                newplayer.firstname = fn
                newplayer.lastname = ln
                newplayer.email = em
                p.insertPlayer(newplayer)

                n.navigate(R.id.nav_players, null)
                true
            }
            R.id.action_delete_player -> {
                var result = "Test"
                val p = PlayerRepo(mContext as MainActivity)
                val player = p.deleteEntry(_id.toString())
                n.navigate(R.id.nav_players, null)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_player_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete_save_player, menu)
        val positionOfMenuItem = 0 // or whatever...
        val item = menu.getItem(positionOfMenuItem)
        val s = SpannableString("Delete")
        s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
        item.title = s
    }


}
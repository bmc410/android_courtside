package com.bmc.courtside.ui.teamplayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.adapters.TeamPlayerAdapter
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.TeamPlayer
import com.bmc.courtside.repos.PlayerRepo
import com.bmc.courtside.repos.TeamRepo


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamPlayersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamPlayersFragment : Fragment() {

    private lateinit var playerrepo: PlayerRepo
    private lateinit var teamrepo: TeamRepo
    private lateinit var adapter: TeamPlayerAdapter
    private lateinit var players: ArrayList<Player>
    private lateinit var context: MainActivity
    private var playerdisplay: ArrayList<String> = ArrayList()
    var _id: Number = -1

    fun refreshData() {
        context = getContext() as MainActivity
        playerrepo = PlayerRepo(context)
        teamrepo = TeamRepo(context)
        players = playerrepo.getPlayers()
        adapter = TeamPlayerAdapter(context, players)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null) {
            _id = arguments?.get("id") as Number
        }

        refreshData()

        val lv = context.findViewById<ListView>(R.id.team_players_list_view)
        lv.adapter = adapter
        lv.choiceMode = ListView.CHOICE_MODE_MULTIPLE


        val btn = context.findViewById<Button>(R.id.btn_AddPlayers)
        btn.setOnClickListener {

            for (i in 0 until adapter.count) {
                val lv = context.findViewById<ListView>(R.id.team_players_list_view)

                val c = lv.findViewById(R.id.simpleCheckedTextView) as CheckedTextView
                val checkedValue: Boolean = c.isChecked // check current state of CheckedText
                if( checkedValue) {
                    var t = teamrepo.getTeam(_id)
                    var player = adapter.getItem(i)
                    var id = player.id
                    //var tp = TeamPlayer(null, player.id, t?.teamid,"", t?.year?.toInt())
                    var teamplayer: TeamPlayer = TeamPlayer()
                    teamplayer.playerid = player.id
                    if (t != null) {
                        teamplayer.teamid = t.id
                    }
                    teamplayer.jersey = ""
                    if (t != null) {
                        teamplayer.year = t.year?.toInt()
                    }
                    teamrepo.insertTeamPlayer(teamplayer)

                }
            }

        }
            //return names
        }

            //val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
            //n.navigateUp()
        //}




        //this.listView.setAdapter(arrayAdapter)
        //adapter.setCallback((this))



//        lv.setOnItemClickListener { parent, view, position, id ->
//
//            val n = Navigation.findNavController(context, R.id.nav_host_fragment_content_main)
//
//            //val fragment: Fragment = TeamDetailFragment()
//
//            //val args = Bundle()
//            val bundle = bundleOf("id" to position)
//            bundle.putInt("id", players[position].id as Int)
//
//
//            //fragmentManager?.putFragment(bundle, "", fragment)
//            n.navigate(R.id.action_nav_players_to_playerDetailFragment, bundle)
//
//        }

    //}



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }


}
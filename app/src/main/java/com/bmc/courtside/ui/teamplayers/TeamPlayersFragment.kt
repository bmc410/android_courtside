package com.bmc.courtside.ui.teamplayers

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    }

    inner class getTeamPlayerData(c: Context) : AsyncTask<Void, Void, String>() {
        var context = c

        fun getTeamData(context: Context) {

        }

        override fun doInBackground(vararg params: Void?): String? {
            context = getContext() as MainActivity
            playerrepo = PlayerRepo(context)
            teamrepo = TeamRepo(context)
            players = playerrepo.getPlayers()
            return ""
        }

        override fun onPreExecute() {
            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            setupUI()
        }
    }

    fun setupUI() {

        context = requireContext() as MainActivity

        val recyclerview = context.findViewById<RecyclerView>(R.id.team_players_recycler_view)
        var teamplayers = teamrepo.getTeamPlayers(_id)
        for (player in players) {
            if (teamplayers != null) {
                player.isSelected = teamplayers.filter { tp -> tp.playerid == player.id}.count() > 0
            }
        }

        adapter = TeamPlayerAdapter(players)

        var ll: LinearLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = ll;
        recyclerview.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context = requireContext() as MainActivity

        if (arguments != null) {
            _id = arguments?.get("id") as Number
        }

        getTeamPlayerData(requireContext()).execute()

        val btn = context.findViewById<Button>(R.id.btn_AddPlayers)
        btn.setOnClickListener {

            teamrepo.deleteAllTeamPlayers(_id)
            var team = teamrepo.getTeam((_id))

            for (player in adapter.checkedPlayers) {
                var teamplayer: TeamPlayer = TeamPlayer()
                    teamplayer.playerid = player.id
                    teamplayer.teamid = _id
                    teamplayer.jersey = ""
                    teamplayer.year = team?.year?.toInt()
                    teamrepo.insertTeamPlayer(teamplayer)
            }
            val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
            n.navigateUp()

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }


}
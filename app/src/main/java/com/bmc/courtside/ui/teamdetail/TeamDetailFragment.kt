@file:Suppress("SpellCheckingInspection")

package com.bmc.courtside.ui.teamdetail

import CustomAdapter
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.models.Club
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.Team
import com.bmc.courtside.models.TeamPlayer
import com.bmc.courtside.repos.ClubRepo
import com.bmc.courtside.repos.PlayerRepo
import com.bmc.courtside.repos.TeamRepo
import java.util.*


class TeamDetailFragment : Fragment() {

    //private lateinit var playersAdapter: TeamDetailPlayersAdapter
    private lateinit var teamrepo: TeamRepo
    private lateinit var playerrepo: PlayerRepo
    //private lateinit var tf: TeamsFragment
    private lateinit var clubs: ArrayList<Club>
    private lateinit var players: ArrayList<Player>
    private lateinit var context: MainActivity
    private lateinit var teamplayers: ArrayList<TeamPlayer>

    var years: MutableList<String> = ArrayList()
    private val clubsArray: MutableList<String> = ArrayList()


    var _id: Number = -1
    var teamname: String = ""
    var y: String? = ""
    var cl: Number? = -1



    inner class getTeamData(c: Context) : AsyncTask<Void, Void, String>() {
        var context = c

        fun getTeamData(context: Context) {

        }

        override fun doInBackground(vararg params: Void?): String? {
            playerrepo = PlayerRepo(context)
            teamrepo = TeamRepo((context))
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
        //var lv = context.findViewById<ListView>(R.id.teams_players_list_view)

        if(arguments != null) {
            _id = arguments?.get("id") as Number
        }
        if(_id != -1) {
            teamplayers = ArrayList<TeamPlayer>()
            teamplayers = teamrepo.getTeamPlayers(_id) as ArrayList<TeamPlayer>



            // getting the recyclerview by its id
            val recyclerview = context.findViewById<RecyclerView>(R.id.matches_listview)

            val data = ArrayList<Player>()

            // This will pass the ArrayList to our Adapter
            val adapter = CustomAdapter(teamplayers)

            // Setting the Adapter with the recyclerview
            var ll: LinearLayoutManager = LinearLayoutManager(activity)
            recyclerview.layoutManager = ll;


            setRecyclerViewItemTouchListener()

            recyclerview.adapter = adapter

        }

        var addBtn: Button = view?.findViewById(R.id.btn_add_players_to_team) as Button
        addBtn.setOnClickListener {
            val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
            val bundle: Bundle = Bundle()
            bundle.putInt("id", _id as Int)
            n.navigate(R.id.action_nav_team_detail_to_nav_team_players, bundle)

        }

        if(arguments != null) {
            _id = arguments?.get("id") as Number
        }
        if(_id != -1) {
            var team = teamrepo.getTeam(_id)
            players = playerrepo.getPlayers()


            teamname = team?.teamname.toString()
            cl = team?.clubid
            y = team?.year
            (context as MainActivity).supportActionBar?.title = teamname
            //team name
            var textView: TextView = (context as MainActivity).findViewById(R.id.txt_opponent) as TextView
            textView.text = teamname



        }


        val c = ClubRepo(context as MainActivity)
        clubs = c.getClubs()

        for (c in clubs) {
            clubsArray.add(c.clubname)
        }

        //Year spinner
        var tv = requireView().findViewById(R.id.txt_match_date) as AutoCompleteTextView
        val adYears = ArrayAdapter(requireContext(), R.layout.list_item, years)
        tv.setAdapter(adYears)
        if (y != null && y != "") {
            tv.setText(y, false)
        }


        //Club spinner
        var cv = requireView().findViewById(R.id.player_team_spinner) as AutoCompleteTextView
        val adClubs = ArrayAdapter(requireContext(), R.layout.list_item, clubsArray)
        cv.setAdapter(adClubs)
        if (cl != null && cl != -1) {
            cv.setText(clubs.filter { it.clubid == cl }[0].clubname, false)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        var from = Calendar.getInstance().get(Calendar.YEAR) - 5;
        var to = Calendar.getInstance().get(Calendar.YEAR) + 5;
        for (i in from..to) {
            years.add(i.toString())
        }
        return inflater.inflate(R.layout.team_detail_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete_save_team, menu)
        val positionOfMenuItem = 0 // or whatever...
        val item = menu.getItem(positionOfMenuItem)
        val s = SpannableString("Delete")
        s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
        item.title = s
    }

    private fun setRecyclerViewItemTouchListener() {
        val recyclerview = context.findViewById<RecyclerView>(R.id.matches_listview)
        //1
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                var id = teamplayers[position].id
                teamrepo.deleteEntry("TeamPlayers", id!!)
                //teamrepo.deleteAllTeamPlayers()
                teamplayers.removeAt(position)
                recyclerview.adapter!!.notifyItemRemoved(position)
                recyclerview.adapter!!.notifyDataSetChanged()
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getTeamData(requireContext()).execute()

//        refreshData()
//        //var lv = context.findViewById<ListView>(R.id.teams_players_list_view)
//
//        if(arguments != null) {
//            _id = arguments?.get("id") as Number
//        }
//        if(_id != -1) {
//            teamplayers = ArrayList<TeamPlayer>()
//            teamplayers = teamrepo.getTeamPlayers(_id) as ArrayList<TeamPlayer>
//
//
//
//            // getting the recyclerview by its id
//            val recyclerview = context.findViewById<RecyclerView>(R.id.recyclerview)
//
//            val data = ArrayList<Player>()
//
//            // This will pass the ArrayList to our Adapter
//            val adapter = CustomAdapter(teamplayers)
//
//            // Setting the Adapter with the recyclerview
//            var ll: LinearLayoutManager = LinearLayoutManager(activity)
//            recyclerview.layoutManager = ll;
//
//
//            setRecyclerViewItemTouchListener()
//
//            recyclerview.adapter = adapter
//
//        }
//
//        var addBtn: Button = view.findViewById(R.id.btn_add_players_to_team) as Button
//        addBtn.setOnClickListener {
//            val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
//            val bundle: Bundle = Bundle()
//            bundle.putInt("id", _id as Int)
//            n.navigate(R.id.action_nav_team_detail_to_nav_team_players, bundle)
//
//        }
//
//        if(arguments != null) {
//            _id = arguments?.get("id") as Number
//        }
//        if(_id != -1) {
//            var team = teamrepo.getTeam(_id)
//            players = playerrepo.getPlayers()
//
//
//            teamname = team?.teamname.toString()
//            cl = team?.clubid
//            y = team?.year
//            (context as MainActivity).supportActionBar?.title = teamname
//            //team name
//            var textView: TextView = (context as MainActivity).findViewById(R.id.team_detail_team_name) as TextView
//            textView.text = teamname
//
//
//
//        }


//        val c = ClubRepo(context as MainActivity)
//        clubs = c.getClubs()
//
//        for (c in clubs) {
//            clubsArray.add(c.clubname)
//        }
//
//        //Year spinner
//        var tv = view.findViewById(R.id.year_spinner) as AutoCompleteTextView
//        val adYears = ArrayAdapter(requireContext(), R.layout.list_item, years)
//        tv.setAdapter(adYears)
//        if (y != null && y != "") {
//            tv.setText(y, false)
//        }


//        //Club spinner
//        var cv = view.findViewById(R.id.player_team_spinner) as AutoCompleteTextView
//        val adClubs = ArrayAdapter(requireContext(), R.layout.list_item, clubsArray)
//        cv.setAdapter(adClubs)
//        if (cl != null && cl != -1) {
//            cv.setText(clubs.filter { it.clubid == cl }[0].clubname, false)
//
//        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
        return when (item.itemId) {
            R.id.action_save -> {
                var textView: TextView = (context as MainActivity).findViewById(R.id.txt_opponent) as TextView
                val teamName = textView.text.toString()
                var cv = (context as MainActivity).findViewById(R.id.player_team_spinner) as AutoCompleteTextView
                val club = cv.text.toString()
                var tv = (context as MainActivity).findViewById(R.id.txt_match_date) as AutoCompleteTextView
                val year = tv.text.toString()
                val t = TeamRepo(context as MainActivity)
                //val newteam = Team(-1, teamName, 1, year)
                val newteam = Team()
                newteam.teamname = teamname
                newteam.clubid = 1
                newteam.year = year
                t.insertTeam((newteam))
                setFragmentResult("requestKey", bundleOf("bundleKey" to ""))
                //parentFragmentManager?.popBackStack()
                //n.graph.clear()

                n.navigate(R.id.nav_teams, null)
                //n.popBackStack(R.id.nav_teams, true)
                true
            }
            R.id.action_delete -> {
                var result = "Test"
                val t = TeamRepo(context as MainActivity)
                val team = t.deleteEntry("Teams", _id)
                //setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                //parentFragmentManager.popBackStack()
                n.navigateUp()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }


}
package com.bmc.courtside.ui.matchdetail

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.models.Match
import com.bmc.courtside.models.Team
import com.bmc.courtside.repos.MatchRepo
import com.bmc.courtside.repos.TeamRepo
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MatchDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchDetailFragment : Fragment() {

    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    private lateinit var matchrepo: MatchRepo
    private lateinit var teamrepo: TeamRepo
    //private lateinit var tf: TeamsFragment
    private lateinit var teams: ArrayList<Team>
    //private lateinit var players: ArrayList<Player>
    private lateinit var context: MainActivity
    //private lateinit var teamplayers: ArrayList<TeamPlayer>

    //var years: MutableList<String> = ArrayList()
    //private val clubsArray: MutableList<String> = ArrayList()
    private val teamsArray: MutableList<String> = java.util.ArrayList()

    var id: Number = -1
    var home: String? = ""
    var hometeamid: Number? = null
    var opponent: String? = ""
    var matchdate: String? = ""
    var displaydate: String? = ""
    var matchdisplay: String? = ""
    //var teamname: String = ""
    //var y: String? = ""
    //var cl: Number? = -1

    inner class getMatchData(c: Context) : AsyncTask<Void, Void, String>() {
        var context = c

        fun getMatchData(context: Context) {

        }

        override fun doInBackground(vararg params: Void?): String? {
            matchrepo = MatchRepo(context)
            teamrepo = TeamRepo((context))



            teams = teamrepo.getTeams() as ArrayList<Team>
            for (t in teams) {
                teamsArray.add(t.teamname!!)
            }
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_detail, container, false)
    }



    fun setupUI() {

//        var hometv: TextView = (context as MainActivity).findViewById(R.id.home_team_spinner) as AutoCompleteTextView
//        //val teamName = textView.text.toString()
//        var opp = (context as MainActivity).findViewById(R.id.txt_opponent) as EditText
//        //val club = cv.text.toString()
//        var matchdate = (context as MainActivity).findViewById(R.id.txt_match_date) as AutoCompleteTextView


        //Opponent
        var txtOpp: TextView = (context as MainActivity).findViewById(R.id.txt_opponent) as TextView

        //Team spinner
        var tv = requireView().findViewById(R.id.home_team_spinner) as AutoCompleteTextView
        val adTeams = ArrayAdapter(requireContext(), R.layout.list_item, teamsArray)
        tv.setAdapter(adTeams)

        var dtv = requireView().findViewById(R.id.txt_match_date) as EditText
        dtv.setText("")
        //dtv.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var hometv = requireView().findViewById(R.id.txtHeaderHomeTeam) as TextView
        hometv.text = ""

        var opptv = requireView().findViewById(R.id.txtHeaderOpponentTeam) as TextView
        hometv.text = ""

        val p1: ImageButton = requireView().findViewById(R.id.btnP1)
        val p2: ImageButton = requireView().findViewById(R.id.btnP2)
        val p3: ImageButton = requireView().findViewById(R.id.btnP3)
        val p4: ImageButton = requireView().findViewById(R.id.btnP4)
        val p5: ImageButton = requireView().findViewById(R.id.btnP5)

        p1.setOnClickListener {
            showGame(1)
        }
        p2.setOnClickListener {
            // Do something in response to button click
        }
        p3.setOnClickListener {
            // Do something in response to button click
        }
        p4.setOnClickListener {
            // Do something in response to button click
        }
        p5.setOnClickListener {
            // Do something in response to button click
        }



        dtv.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        if(id != -1) {
            var match = matchrepo.getMatch(id)
            hometv.text = match.home
            opptv.text = match.opponent
            dtv.setText(match.matchdate)
            txtOpp.setText(match.opponent)
            tv.setText(match.home)
            //textView.text = teamname
        }



    }

    fun showGame(id: Number) {
        val n = Navigation.findNavController(context, R.id.nav_host_fragment_content_main)

        //val fragment: Fragment = TeamDetailFragment()

        //val args = Bundle()
        val bundle =  Bundle()

        //fragmentManager?.putFragment(bundle, "", fragment)
        n.navigate(R.id.action_matchDetailFragment_to_matchFragment, bundle)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete_save_match, menu)
        val positionOfMenuItem = 0 // or whatever...
        val item = menu.getItem(positionOfMenuItem)
        val s = SpannableString("Delete")
        s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
        item.title = s
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val n = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_content_main)
        return when (item.itemId) {
            R.id.action_save_match -> {
                var hometv: TextView = (context as MainActivity).findViewById(R.id.home_team_spinner) as AutoCompleteTextView
                //val teamName = textView.text.toString()
                var opp = (context as MainActivity).findViewById(R.id.txt_opponent) as EditText
                //val club = cv.text.toString()
                var matchdate = (context as MainActivity).findViewById(R.id.txt_match_date) as AutoCompleteTextView
                //val year = tv.text.toString()
                //val repo = MatchRepo(context as MainActivity)
                //val newteam = Team(-1, teamName, 1, year)
                val newmatch = Match()
                newmatch.matchdate = matchdate.text.toString()
                newmatch.opponent = opp.text.toString()
                var p = teams.filter { it.teamname == hometv.text.toString() }
                newmatch.hometeamid = teams.filter { it.teamname == hometv.text.toString() }[0].id
                newmatch.home = hometv.text.toString()
                matchrepo.insertMatch((newmatch))
                setFragmentResult("requestKey", bundleOf("bundleKey" to ""))
                //parentFragmentManager?.popBackStack()
                //n.graph.clear()

                n.navigateUp()
                true
            }
            R.id.action_delete_match -> {
                matchrepo.deleteMatch("Matches", id)
                n.navigateUp()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context = requireContext() as MainActivity
        if(arguments != null) {
            id = arguments?.get("id") as Number
        }

        getMatchData(requireContext()).execute()


    }
}


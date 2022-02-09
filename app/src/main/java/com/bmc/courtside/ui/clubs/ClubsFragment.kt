package com.bmc.courtside.ui.clubs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.adapters.ClubsAdapter
import com.bmc.courtside.adapters.TeamsAdapter
import com.bmc.courtside.repos.ClubRepo
import com.bmc.courtside.repos.TeamRepo

class ClubsFragment : Fragment() {

    companion object {
        fun newInstance() = ClubsFragment()
    }

    private lateinit var viewModel: ClubsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.clubs_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context as MainActivity
        val c = ClubRepo(context)
        val clubs = c.getClubs()

        val adapter = ClubsAdapter(context, clubs)
        val lv = context.findViewById<ListView>(R.id.clubs_list_view)
        lv.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClubsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
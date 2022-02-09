package com.bmc.courtside.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filterable
import android.widget.TextView
import com.bmc.courtside.R
import com.bmc.courtside.models.Club
import com.bmc.courtside.models.Team



class ClubsAdapter(context: Context, clubs: List<Club>): BaseAdapter() {
    private val context = context
    private val clubs = clubs

    override fun getCount(): Int {
        return clubs.count()
    }

    override fun getItem(position: Int): Any {
        return clubs[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //categoryView = LayoutInflater.from(context).inflate(R.layout.activity_heroes, null)
        val listView = LayoutInflater.from(context).inflate(R.layout.club_listrow, null)
        val club = getItem(position) as Club


        val titleTextView = listView.findViewById(R.id.club_name) as TextView
        titleTextView.text = club.clubname

        //heroText.text = category

        return listView

    }
}

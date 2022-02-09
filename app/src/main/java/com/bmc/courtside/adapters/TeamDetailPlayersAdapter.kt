package com.bmc.courtside.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bmc.courtside.R
import com.bmc.courtside.models.Player


class TeamDetailPlayersAdapter(context: Context, players: ArrayList<Player>): BaseAdapter() {
    private val context = context
    private val players = players

    override fun getCount(): Int {
        return players.count()
    }

    override fun getItem(position: Int): Any {
        return players[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.full_name_row, null)
        val player = getItem(position) as Player

        val titleTextView = view.findViewById(R.id.txt_player_row_name) as TextView
        titleTextView.text = player.firstname + " " + player.lastname
        return view

    }
}

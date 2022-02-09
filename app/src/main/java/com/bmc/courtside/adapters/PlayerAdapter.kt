package com.bmc.courtside.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bmc.courtside.R
import com.bmc.courtside.models.Player


class PlayerAdapter(context: Context, players: List<Player>): BaseAdapter() {
    private val context = context
    private val players = players
    private val _this = context

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

        val listView = LayoutInflater.from(_this).inflate(R.layout.player_listrow, null)
        val player = getItem(position) as Player

        val titleTextView = listView.findViewById(R.id.txt_player_fullname) as TextView
        titleTextView.text = player.firstname + " " + player.lastname

        val email = listView.findViewById(R.id.txt_player_emailaddress) as TextView
        email.text = player.email



        return listView

    }
}

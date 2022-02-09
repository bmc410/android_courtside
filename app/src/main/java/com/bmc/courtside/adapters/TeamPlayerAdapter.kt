package com.bmc.courtside.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.Toast
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.models.Player
import com.bmc.courtside.repos.TeamRepo


class TeamPlayerAdapter (context: Context, players: List<Player>): BaseAdapter() {
    private val context = context
    private val players = players

    override fun getCount(): Int {
        return players.count()
    }

    override fun getItem(position: Int): Player {
        return players[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val listView = LayoutInflater.from(context).inflate(R.layout.teamplayer_listrow, null)
        val player = getItem(position) as Player

        val c = listView.findViewById(R.id.simpleCheckedTextView) as CheckedTextView
        c.setText(player.firstname + " " + player.lastname)

        val t = TeamRepo(context as MainActivity)


        c.setOnClickListener(View.OnClickListener {
            val value: Boolean = c.isChecked
            if (value) {
                // set check mark drawable and set checked property to false
                c.setCheckMarkDrawable(R.drawable.ic_check_box_empty)
                c.isSelected = false
                c.isChecked = false

            } else {
                // set check mark drawable and set checked property to true
                c.setCheckMarkDrawable(R.drawable.ic_check_black)
                c.isSelected = true
                c.isChecked = true

            }
        })

        return listView

    }
}
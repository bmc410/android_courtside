package com.bmc.courtside.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bmc.courtside.MainActivity
import com.bmc.courtside.R
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.TeamPlayer
import com.bmc.courtside.repos.TeamRepo


class TeamPlayerAdapter(private val players: ArrayList<Player>) : RecyclerView.Adapter<TeamPlayerAdapter.TPHolder>() {

    var checkedPlayers: ArrayList<Player> = ArrayList<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayerAdapter.TPHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teamplayer_listrow, parent, false)

        return TPHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: TPHolder, position: Int) {
        val player = players[position]


        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.nameTxt.text = player.firstname + " " + player.lastname
        holder.mCheckBox.isChecked = player.isSelected

        holder.setItemClickListener(object : TPHolder.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val myCheckBox = v as CheckBox
                val currentPlayer = players[pos]

                if (myCheckBox.isChecked) {
                    currentPlayer.isSelected = true
                    checkedPlayers.add(currentPlayer)
                } else if (!myCheckBox.isChecked) {
                    currentPlayer.isSelected = false
                    checkedPlayers.remove(currentPlayer)
                }
            }
        })
    }






     class TPHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        lateinit var myItemClickListener: ItemClickListener
        var nameTxt: TextView = itemView.findViewById(R.id.textplayer_playername)
         var mCheckBox: CheckBox = itemView.findViewById(R.id.teamplayer_checkbox)


         init {
             mCheckBox.setOnClickListener(this)
         }

         interface ItemClickListener {

            fun onItemClick(v: View, pos: Int)
        }
        fun setItemClickListener(ic: TPHolder.ItemClickListener) {
            this.myItemClickListener = ic
        }

        override fun onClick(v: View) {
            this.myItemClickListener.onItemClick(v, layoutPosition)
        }

    }

}


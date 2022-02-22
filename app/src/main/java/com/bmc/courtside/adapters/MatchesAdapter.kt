package com.bmc.courtside.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmc.courtside.R
import com.bmc.courtside.interfaces.ClickListener
import com.bmc.courtside.models.Match
import com.bmc.courtside.models.TeamPlayer

class MatchesAdapter(private val matches: ArrayList<Match>) : RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(this)
            }
        }

        var home: TextView = itemView.findViewById(R.id.txtHome)
        var opp: TextView = itemView.findViewById(R.id.txtOpponent)
        var matchdate: TextView = itemView.findViewById(R.id.txtMatchDate)
        override fun onClick(v: View?) {
            if (v != null) {
                clickListener?.onItemClick(v,adapterPosition)
            }
        }

    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_listrow, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = matches[position]


        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.home.text = m.home
        holder.opp.text = m.opponent
        holder.matchdate.text = m.matchdate
    }

    override fun getItemCount(): Int {
        return matches.size
    }


}
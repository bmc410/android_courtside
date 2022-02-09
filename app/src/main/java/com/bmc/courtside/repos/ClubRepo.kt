package com.bmc.courtside.repos

import android.content.Context
import android.database.Cursor
import com.bmc.courtside.helpers.DbHelper
import com.bmc.courtside.models.Club
import com.bmc.courtside.models.Team

class ClubRepo(context : Context) {
    private val c = context
    fun getClubs(): ArrayList<Club> {
        val clubs: ArrayList<Club> = ArrayList<Club>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Clubs"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var club = Club()
            club.clubid = c.getInt(0)
            club.clubname = c.getString(1)
            clubs.add(club)
        }
        db.close()
        return clubs
    }
}
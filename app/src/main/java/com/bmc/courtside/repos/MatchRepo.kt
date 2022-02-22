package com.bmc.courtside.repos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.bmc.courtside.helpers.DbHelper
import com.bmc.courtside.models.Match
import com.bmc.courtside.models.Team

class MatchRepo(context : Context) {
    private val c = context


    fun getMatches(): ArrayList<Match> {
        val matches: ArrayList<Match> = ArrayList<Match>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Matches"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var match = Match() as Match
            match.id = c.getInt(0)
            match.hometeamid = c.getInt(1)
            match.home = c.getString(2)
            match.opponent = c.getString(3)
            match.matchdate = c.getString(4)
            matches.add(match)
        }
        db.close()
        return matches
    }

    fun getMatch(id: Number): Match {
        val matches: ArrayList<Match> = ArrayList<Match>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Matches where id = '$id'"
        val c: Cursor = db.rawQuery(query, null)
        c.moveToFirst()
        var match = Match() as Match
        match.id = c.getInt(0)
        match.hometeamid = c.getInt(1)
        match.home = c.getString(2)
        match.opponent = c.getString(3)
        match.matchdate = c.getString(4)
        matches.add(match)
        db.close()
        return match
    }

    fun insertMatch(m: Match) {
        val db = DbHelper(c).readableDatabase
        val contentValues = ContentValues()
        var match = Match() as Match
        contentValues.put("homeid", m.hometeamid?.toInt() ?: 0)
        contentValues.put("homename", m.home)
        contentValues.put("opponentname", m.opponent)
        contentValues.put("matchdate", m.matchdate)
        db.insert("Matches", null, contentValues);
    }

    fun deleteAllMatches() {
        val db = DbHelper(c).readableDatabase
        db.execSQL("DELETE FROM Matches")
        db.close()
    }

    fun deleteMatch(Table: String, ID: Number): Int {
        val db = DbHelper(c).readableDatabase
        var sql = "DELETE FROM " + Table + " WHERE id = " + ID
        db.execSQL(sql)
        db.close()
        return 1
    }

}
package com.bmc.courtside.repos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.bmc.courtside.helpers.DbHelper
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.Team
import com.bmc.courtside.models.TeamPlayer


class TeamRepo(context : Context) {
    private val c = context



    fun getTeams(): ArrayList<Team> {
        val teams: ArrayList<Team> = ArrayList<Team>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Teams"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var team = Team() as Team
            team.id = c.getInt(0)
            team.teamname = c.getString(1)
            team.clubid = c.getInt(2)
            team.year = c.getString(3)
            teams.add(team)
        }
        db.close()
        return teams
    }

    fun getTeam(id:Number): Team? {
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Teams where id = '$id'"
        val c: Cursor = db.rawQuery(query, null)
        c.moveToFirst()
        var team = Team() as Team
        team.id = c.getInt(0)
        team.teamname = c.getString(1)
        team.clubid = c.getInt(2)
        team.year = c.getString(3)
        db.close()
        return team
    }

    fun getTeamPlayers(id:Number): List<TeamPlayer>? {
        val teamplayers: ArrayList<TeamPlayer> = ArrayList<TeamPlayer>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM TeamPlayers where teamid = '$id'"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var teamplayer: TeamPlayer = TeamPlayer()
            teamplayer.id = c.getInt(0)
            teamplayer.playerid = c.getInt(1)
            teamplayer.teamid = id
            teamplayer.jersey = c.getString(3)
            teamplayer.year = c.getString(4).toInt()
            teamplayers.add(teamplayer)
        }
        db.close()
        return teamplayers
    }

    fun insertTeam(t: Team) {
        val db = DbHelper(c).readableDatabase
        val contentValues = ContentValues()
        contentValues.put("teamname", t.teamname)
        contentValues.put("clubid", 1)
        contentValues.put("year", 2022)
        db.insert("Teams", null, contentValues);
    }

    fun insertTeamPlayer(tp: TeamPlayer) {
        val db = DbHelper(c).readableDatabase
        val contentValues = ContentValues()
        contentValues.put("playerid", tp.playerid?.toInt() ?: null)
        contentValues.put("teamid", tp.teamid?.toInt() ?: null)
        contentValues.put("jersey", tp.jersey)
        contentValues.put("clubyear", tp.year?.toInt() ?: null)

        var res = db.insert("TeamPlayers", null, contentValues)
        var t = res
    }

    fun deleteEntry(ID: String): Int {
        val where = "id=?"
        val db = DbHelper(c).readableDatabase
        return db.delete("Teams", where, arrayOf(ID))
    }

}
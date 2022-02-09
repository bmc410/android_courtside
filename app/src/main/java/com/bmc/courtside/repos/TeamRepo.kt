package com.bmc.courtside.repos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bmc.courtside.helpers.DbHelper
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

    fun getTeamPlayers(id:Number): ArrayList<TeamPlayer>? {

        var playerrepo: PlayerRepo = PlayerRepo(c)
        var players = playerrepo.getPlayers()
        val teamplayers: ArrayList<TeamPlayer> = ArrayList<TeamPlayer>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM TeamPlayers where teamid = '$id'"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var teamplayer: TeamPlayer = TeamPlayer()
            teamplayer.id = c.getInt(0)
            teamplayer.firstname = players.filter { player -> player.id == c.getInt(1) }.first().firstname
            teamplayer.lastname = players.filter { player -> player.id == c.getInt(1) }.first().lastname
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


   fun deleteAllTeamPlayers() {
       val db = DbHelper(c).readableDatabase
       db.execSQL("DELETE FROM TeamPlayers")
       db.close()
   }

    fun deleteEntry(Table: String, ID: Number): Int {
        val db = DbHelper(c).readableDatabase
        var sql = "DELETE FROM " + Table + " WHERE id = " + ID
        db.execSQL(sql)
        db.close()
        return 1
    }

}
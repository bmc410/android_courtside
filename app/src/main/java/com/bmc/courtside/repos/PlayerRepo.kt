package com.bmc.courtside.repos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.bmc.courtside.helpers.DbHelper
import com.bmc.courtside.models.Club
import com.bmc.courtside.models.Player
import com.bmc.courtside.models.Team

class PlayerRepo(context : Context) {

    private val c = context

    fun getPlayers(): ArrayList<Player> {
        val players: ArrayList<Player> = ArrayList<Player>()
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Players"
        val c: Cursor = db.rawQuery(query, null)
        while (c.moveToNext()) {
            var player: Player = Player()
            player.firstname = c.getString(1)
            player.email = c.getString(3)
            player.id = c.getInt(0)
            player.lastname = c.getString(2)
            players.add(player)
        }
        db.close()
        return players
    }

    fun getPlayer(id:Number): Player? {
        val db = DbHelper(c).readableDatabase
        val query = "SELECT * FROM Players where id = '$id'"
        val c: Cursor = db.rawQuery(query, null)
        c.moveToFirst()
            var player: Player = Player()
            player.firstname = c.getString(1)
            player.email = c.getString(3)
            player.id = c.getInt(0)
            player.lastname = c.getString(2)
            db.close()
        db.close()
        return player
    }

    fun insertPlayer(p: Player) {
        val db = DbHelper(c).readableDatabase
        val contentValues = ContentValues()
        contentValues.put("firstname", p.firstname)
        contentValues.put("lastname", p.lastname)
        contentValues.put("email", p.email)
        db.insert("Players", null, contentValues);
    }

    fun deleteEntry(ID: String): Int {
        val where = "id=?"
        val db = DbHelper(c).readableDatabase
        return db.delete("Players", where, arrayOf(ID))
    }
}
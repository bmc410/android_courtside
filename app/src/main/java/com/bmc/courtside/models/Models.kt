package com.bmc.courtside.models

class Team {
    var id: Number? = null
    var teamname: String? = null
    var clubid: Number? = null
    var year: String? = ""
}

class Club{
    var clubid: Number? = null
    var clubname: String = ""
}

class TeamPlayer {
    var id: Number? = null
    var playerid: Number? = null
    var firstname: String = ""
    var lastname: String = ""
    var teamid: Number? = null
    var jersey: String? = null
    var year: Number? = null
}

 class Player {
    var id: Number? = null
    var firstname: String = ""
    var lastname: String = ""
    var email: String = ""
}
 class Game{
     var gameid: Number? = null
     var gamenumber: Number? = null
     var matchid: Number? = null
     var homescore: Number? = null
     var opponentscore: Number? = null
     var subs: Number? = null
     var gamedisplay: String? = ""
     var hometeamlibero: String? = ""
}

 class Match {
     var home: String? = ""
     var hometeamid: Number? = null
     var opponent: String? = ""
     var matchdate: String? = ""
     var displaydate: String? = ""
     var matchdisplay: String? = ""
    //gameDate?: number;
}

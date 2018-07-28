package com.example.mostafaeisam.apifootball.classes;

import com.google.gson.annotations.SerializedName;

public class GetResultMatches {

    private int goalsHomeTeam;
    private int goalsAwayTeam;

    public int getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(int goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public int getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(int goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }
}

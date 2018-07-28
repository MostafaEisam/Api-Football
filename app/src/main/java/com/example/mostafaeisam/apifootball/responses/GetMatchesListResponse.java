package com.example.mostafaeisam.apifootball.responses;

import com.example.mostafaeisam.apifootball.classes.Match;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetMatchesListResponse {

    @SerializedName("fixtures")
    private ArrayList<Match> matchesList;

    public ArrayList<Match> getMatchesList() {
        return matchesList;
    }

    public void setMatchesList(ArrayList<Match> matchesList) {
        this.matchesList = matchesList;
    }
}

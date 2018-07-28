package com.example.mostafaeisam.apifootball.classes;

import com.example.mostafaeisam.apifootball.responses.GetLinks;

public class Match {
    String date;
    String status;
    String homeTeamName;
    String awayTeamName;
    private GetResultMatches result;
    private GetLinks _links;
    boolean isFavorite;


    public Match(String status, String homeTeamName, String awayTeamName,GetResultMatches result,GetLinks _links) {
        this.status = status;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.result = result;
        this._links = _links;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public GetResultMatches getResult() {
        return result;
    }

    public void setResult(GetResultMatches result) {
        this.result = result;
    }

    public GetLinks get_links() {
        return _links;
    }

    public void set_links(GetLinks _links) {
        this._links = _links;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

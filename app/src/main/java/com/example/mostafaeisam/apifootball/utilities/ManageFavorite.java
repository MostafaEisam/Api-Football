package com.example.mostafaeisam.apifootball.utilities;

import android.content.Context;
import com.example.mostafaeisam.apifootball.classes.Match;
import com.example.mostafaeisam.apifootball.responses.GetMatchesListResponse;

import java.util.ArrayList;

public class ManageFavorite {
    public static void addMatchesIdtoFivorite(Context context, Match match){
        ArrayList<Match> mMatchesList = getFavoriteMatches(context);
        mMatchesList.add(match);

        SavePrefs<GetMatchesListResponse> mMatchesListResponseSavePrefs = new SavePrefs<>(context,GetMatchesListResponse.class);

        GetMatchesListResponse mMatchesListResponse = new GetMatchesListResponse();
        mMatchesListResponse.setMatchesList(mMatchesList);
        mMatchesListResponseSavePrefs.save(mMatchesListResponse);
    }

    public static ArrayList<Match> getFavoriteMatches(Context context){
        // 1- First Step
        SavePrefs<GetMatchesListResponse> mMatchesListResponseSavePrefs = new SavePrefs<>(context,GetMatchesListResponse.class);
        GetMatchesListResponse mMatchesListResponse = mMatchesListResponseSavePrefs.load();
        if (mMatchesListResponse==null)
        {
            return new ArrayList<>();
        }
        else
        {
            return mMatchesListResponse.getMatchesList();
        }
    }

    public static void removeMatchesIdtoFivorite(Context context,Match match){
        ArrayList<Match> mMatchesList = getFavoriteMatches(context);
        for (int i = 0 ; i< mMatchesList.size(); i++ ){
            if (match.get_links().getSelf().getHref().equals(mMatchesList.get(i).get_links().getSelf().getHref())){
                mMatchesList.remove(i);
                break;
            }
        }

        SavePrefs<GetMatchesListResponse> mMatchesListResponseSavePrefs = new SavePrefs<>(context,GetMatchesListResponse.class);

        GetMatchesListResponse mMatchesListResponse = new GetMatchesListResponse();
        mMatchesListResponse.setMatchesList(mMatchesList);
        mMatchesListResponseSavePrefs.save(mMatchesListResponse);
    }
}

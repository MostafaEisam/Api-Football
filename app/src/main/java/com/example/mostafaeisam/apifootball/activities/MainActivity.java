package com.example.mostafaeisam.apifootball.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mostafaeisam.apifootball.classes.Match;
import com.example.mostafaeisam.apifootball.adapters.FixturesAdapter;
import com.example.mostafaeisam.apifootball.R;
import com.example.mostafaeisam.apifootball.classes.MatchesGroupByDate;
import com.example.mostafaeisam.apifootball.responses.GetMatchesListResponse;
import com.example.mostafaeisam.apifootball.services.RequestListener;
import com.example.mostafaeisam.apifootball.services.ServiceFactory;
import com.example.mostafaeisam.apifootball.utilities.InternetConnection;
import com.example.mostafaeisam.apifootball.utilities.ManageFavorite;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RequestListener {
    private RecyclerView mFixturesRecyclerView;
    private RecyclerView.Adapter mAdapterFixturesRecyclerView;
    private ArrayList<Match> mMatchesList;
    private ProgressBar progressBar;
    private RadioGroup rg_filter;
    private RadioButton rb_allFixtures;
    private RadioButton rb_favoriteFixtures;
   // private ArrayList<Match> matchesAdapterList = new ArrayList<>();
    private ArrayList<MatchesGroupByDate> mAllMatches = new ArrayList<>();
    private Button bt_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fixture Challenge");

        initView();
        CheckingInternetConnection();

        rg_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_favoriteFixtures.isChecked()) {
                    mAllMatches.clear();
                    ArrayList<Match> matches = ManageFavorite.getFavoriteMatches(MainActivity.this);
                    for (int i = 0; i < matches.size(); i++) {
                        matches.get(i).setFavorite(true);
                    }
                    manageAdapterList(matches);
                    mAdapterFixturesRecyclerView.notifyDataSetChanged();
                } else {
                    mAllMatches.clear();
                    mappingFavoriteMatchesToAllMatches(ManageFavorite.getFavoriteMatches(MainActivity.this));
                    manageAdapterList(mMatchesList);
                    mAdapterFixturesRecyclerView.notifyDataSetChanged();
                }
            }
        });
    }

    private void getFixtureData() {
        ServiceFactory.getData(this, "http://api.football-data.org/v1/competitions/445/fixtures", this);
    }

    private void initView() {
        rg_filter = findViewById(R.id.rg_filter);
        rb_allFixtures = findViewById(R.id.rb_allFixtures);
        rb_favoriteFixtures = findViewById(R.id.rb_favoriteFixtures);

        mFixturesRecyclerView = findViewById(R.id.rv_fixtures);
        mFixturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMatchesList = new ArrayList<>();
        mFixturesRecyclerView.setNestedScrollingEnabled(false);

        mAdapterFixturesRecyclerView = new FixturesAdapter(
                mAllMatches, getApplicationContext());
        mFixturesRecyclerView.setAdapter(mAdapterFixturesRecyclerView);

        progressBar = findViewById(R.id.progressBar);

        bt_reload = findViewById(R.id.bt_reload);
    }

    @Override
    public void onSuccess(Object object) {
        //uses to hide the progressBar
        progressBar.setVisibility(View.GONE);

        GetMatchesListResponse mAllMatchesListResponse = new Gson().fromJson((String) object, GetMatchesListResponse.class);
        mMatchesList.addAll(mAllMatchesListResponse.getMatchesList());

        ArrayList<Match> mFavoriteMatches = ManageFavorite.getFavoriteMatches(this);
        mappingFavoriteMatchesToAllMatches(mFavoriteMatches);
        manageAdapterList(mMatchesList);
        mAdapterFixturesRecyclerView.notifyDataSetChanged();
    }

    private void manageAdapterList(ArrayList<Match> matches) {
        if (matches.size() > 0) {
           // String dateFromApi  ="";
            String date2 = "";

            for (int i = 0; i < matches.size(); i++) {
                if (date2.equals(getDate(matches.get(i).getDate()))) {
                    MatchesGroupByDate matchesGroupByDate = new MatchesGroupByDate();
                    matchesGroupByDate.setMatch(matches.get(i));
                    mAllMatches.add(matchesGroupByDate);
                }
                else {
                    date2 = getDate(matches.get(i).getDate());

                        MatchesGroupByDate matchesGroupByDate = new MatchesGroupByDate();
                        matchesGroupByDate.setDate(date2);
                        mAllMatches.add(matchesGroupByDate);

                        matchesGroupByDate = new MatchesGroupByDate();
                        matchesGroupByDate.setMatch(matches.get(i));
                        mAllMatches.add(matchesGroupByDate);
                }
            }
        }
    }

    private String getDate(String datestr)
    {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(datestr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            return   fmtOut.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void mappingFavoriteMatchesToAllMatches(ArrayList<Match> mFavoriteMatches) {
        //uses to make small loop inside big loop to find the favorite match and make it with yellow heart
        for (int i = 0; i < mMatchesList.size(); i++) {
            mMatchesList.get(i).setFavorite(false);
            for (int n = 0; n < mFavoriteMatches.size(); n++) {
                if (mMatchesList.get(i).get_links().getSelf().getHref().equals(mFavoriteMatches.get(n).get_links().getSelf().getHref())) {
                    mMatchesList.get(i).setFavorite(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onFailure(int errorCode) {
        //Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
    }

    private void CheckingInternetConnection() {
        if (InternetConnection.checkConnection(this)) {
            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
            getFixtureData();
            bt_reload.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            bt_reload.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void bt_reload(View view) {
        CheckingInternetConnection();
    }
}
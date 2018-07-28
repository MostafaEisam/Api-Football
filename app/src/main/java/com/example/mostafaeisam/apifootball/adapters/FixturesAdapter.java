package com.example.mostafaeisam.apifootball.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mostafaeisam.apifootball.R;
import com.example.mostafaeisam.apifootball.classes.Match;
import com.example.mostafaeisam.apifootball.classes.MatchesGroupByDate;
import com.example.mostafaeisam.apifootball.utilities.ManageFavorite;

import java.util.List;

public  class FixturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //private List<Match> mMatchesList;
    private List<MatchesGroupByDate> mAllMatches;
    private Context mContext;
    private static final int TYPE_Date = 0;
    private static final int TYPE_MATCH = 1;

    public FixturesAdapter(List<MatchesGroupByDate> mAllMatches, Context context) {
        this.mAllMatches = mAllMatches;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_match, viewGroup , false);
        return new ViewHolder(v);
    */
        LayoutInflater mInflater = LayoutInflater.from ( viewGroup.getContext () );
        switch ( viewType ) {

            case TYPE_Date:
                ViewGroup v = ( ViewGroup ) mInflater.inflate ( R.layout.row_date, viewGroup, false );
                return new DateHolder(v);
            case TYPE_MATCH:
                ViewGroup x = ( ViewGroup ) mInflater.inflate ( R.layout.row_match, viewGroup, false );
                return new MatchHolder(x);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        switch ( viewHolder.getItemViewType () ) {

            case TYPE_Date:
                DateHolder dateHolder =(DateHolder) viewHolder;
                dateHolder.tv_date.setText(mAllMatches.get(i).getDate());
                break;
            case TYPE_MATCH:

                final MatchHolder matchHolder = ( MatchHolder ) viewHolder;

                final Match match = mAllMatches.get(i).getMatch();
                matchHolder.tvHomeTeam.setText(mAllMatches.get(i).getMatch().getHomeTeamName());
                matchHolder.tvAwayTeam.setText(mAllMatches.get(i).getMatch().getAwayTeamName());
                matchHolder.goalsHomeTeam.setText(String.valueOf(mAllMatches.get(i).getMatch().getResult().getGoalsHomeTeam()));
                matchHolder.goalsAwayTeam.setText(String.valueOf(mAllMatches.get(i).getMatch().getResult().getGoalsAwayTeam()));
                //uses to check favorite matches and change button color
                if (match.isFavorite())
                {
                    matchHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
                }
                else
                    matchHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite);

                matchHolder.bt_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //uses to save it in favorite
                        //mMatchesList.get(i).setFavorite(true);
                        //viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
                        //uses to add match to favorite list
                        if (match.isFavorite()){
                            ManageFavorite.removeMatchesIdtoFivorite(mContext,mAllMatches.get(i).getMatch());
                            matchHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite);
                            mAllMatches.get(i).getMatch().setFavorite(false);
                        }else{
                            ManageFavorite.addMatchesIdtoFivorite(mContext,mAllMatches.get(i).getMatch());
                            matchHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
                            mAllMatches.get(i).getMatch().setFavorite(true);
                        }
                    }
                });
                break;

        }
/*
        final Match match = mMatchesList.get(i);
        viewHolder.tvHomeTeam.setText(match.getHomeTeamName());
        viewHolder.tvAwayTeam.setText(match.getAwayTeamName());
        viewHolder.goalsHomeTeam.setText(String.valueOf(match.getResult().getGoalsHomeTeam()));
        viewHolder.goalsAwayTeam.setText(String.valueOf(match.getResult().getGoalsAwayTeam()));
        //uses to check favorite matches and change button color
        if (match.isFavorite())
        {
            viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
        }
        else
            viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite);

        viewHolder.bt_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uses to save it in favorite
                //mMatchesList.get(i).setFavorite(true);
                //viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
                //uses to add match to favorite list
                if (match.isFavorite()){
                    ManageFavorite.removeMatchesIdtoFivorite(mContext,mMatchesList.get(i));
                    viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite);
                    mMatchesList.get(i).setFavorite(false);
                }else{
                    ManageFavorite.addMatchesIdtoFivorite(mContext,mMatchesList.get(i));
                    viewHolder.bt_favorite.setBackgroundResource(R.drawable.ic_favorite_2);
                    mMatchesList.get(i).setFavorite(true);
                }
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mAllMatches.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (mAllMatches.get(position).getDate() != null) {
            viewType = TYPE_Date;
        } else {
            viewType = TYPE_MATCH;
        }
        return viewType;
    }

    public class MatchHolder extends RecyclerView.ViewHolder{
        public TextView tvHomeTeam;
        public TextView tvAwayTeam;
        public TextView goalsHomeTeam;
        public TextView goalsAwayTeam;
        public ImageButton bt_favorite;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeTeam = itemView.findViewById(R.id.tv_homeTeam);
            tvAwayTeam = itemView.findViewById(R.id.tv_awayTeam);
            goalsHomeTeam = itemView.findViewById(R.id.tv_goalsHomeTeam);
            goalsAwayTeam = itemView.findViewById(R.id.tv_goalsAwayTeam);
            bt_favorite = itemView.findViewById(R.id.bt_favorite);
        }
    }

    public class DateHolder extends RecyclerView.ViewHolder{
        public TextView tv_date;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

}

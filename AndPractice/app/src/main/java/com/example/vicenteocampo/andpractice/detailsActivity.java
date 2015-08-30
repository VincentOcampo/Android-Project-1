package com.example.vicenteocampo.andpractice;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        TextView plot = (TextView) findViewById(R.id.moviePlot);
        plot.setText(getPlot(getIntent().getStringExtra("id")));
        plot.setTextColor(Color.WHITE);

        TextView movieInfo = (TextView) findViewById(R.id.movieInfo);
        movieInfo.setText(getMovieInfo(getIntent().getStringExtra("id")));
        movieInfo.setTextColor(Color.WHITE);

        ImageView poster = (ImageView) findViewById(R.id.detailImage);
        poster.setAdjustViewBounds(true);

        Picasso.with(this)
                .load(getPoster(getIntent().getStringExtra("id")))
                .into(poster);
    }

    public String getPoster (String data) {

        String baseString = "http://image.tmdb.org/t/p/w342/";
        String posterString = new String();
        try {
            JSONObject movie = new JSONObject(data);
            posterString = movie.getString("poster_path");
        }catch(JSONException e){}
        return new String(baseString + posterString);
    }

    public String getPlot(String data){
        String plot = "Plot Synopsis: \n";

        try {
            JSONObject movie = new JSONObject(data);
            plot = plot +  movie.getString("overview");
        }catch(JSONException e){}
        return plot;
    }
    public String getMovieInfo(String data){
        String title = "Title: ";
        String release = "Release Date: ";
        String voteAverage = "Vote Average: ";


        try {
            JSONObject movie = new JSONObject(data);
            title = title +  movie.getString("title") + "\n";
            voteAverage = voteAverage + movie.getString("vote_average") ;
            release = release + movie.getString("release_date") + "\n";
        }catch(JSONException e){}
        return title + release + voteAverage;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

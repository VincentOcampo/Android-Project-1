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

public class detailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        TextView info = (TextView) findViewById(R.id.movieInfo);
        info.setText(getIntent().getStringExtra("id"));
        info.setTextColor(Color.WHITE);
        ImageView poster = (ImageView) findViewById(R.id.detailImage);
        poster.setAdjustViewBounds(true);

        Picasso.with(this)
                .load(getPoster(getIntent().getStringExtra("id")))
                .into(poster)
        ;
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

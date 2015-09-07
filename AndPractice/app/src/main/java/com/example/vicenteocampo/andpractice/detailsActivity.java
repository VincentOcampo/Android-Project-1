package com.example.vicenteocampo.andpractice;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicenteocampo.andpractice.data.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int DETAIL_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Cursor cDetails = getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

        cDetails.moveToPosition(getIntent().getIntExtra("id", 0));
        TextView plot = (TextView) findViewById(R.id.moviePlot);
        plot.setText(cDetails.getString(cDetails.getColumnIndex(MovieContract.MovieEntry.COlUMN_SUMMARY)));
        plot.setTextColor(Color.WHITE);

        TextView movieInfo = (TextView) findViewById(R.id.movieInfo);
        movieInfo.setText(cDetails.getString(cDetails.getColumnIndex(MovieContract.MovieEntry.COLUMN_INFO)));
        movieInfo.setTextColor(Color.WHITE);


        ImageView poster = (ImageView) findViewById(R.id.detailImage);
        poster.setAdjustViewBounds(true);
        Bitmap b;
        byte[] res = cDetails.getBlob(cDetails.getColumnIndex(MovieContract.
                MovieEntry.COLUMN_POSTER));


        b = BitmapFactory.decodeByteArray(res, 0, res.length);
        poster.setImageBitmap(b);
        cDetails.close();

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getIntent();
        if( intent == null){
            return null;
        }
        return new CursorLoader(this,intent);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

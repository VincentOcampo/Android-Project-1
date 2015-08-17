package com.example.vicenteocampo.andpractice;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ImageAdapter gridAdapter;

    public MainActivityFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // xml contains fragment that contains grid
        setHasOptionsMenu(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
            switch(item.getItemId()){
                case R.id.sort_popular:
                    new fetchMovieImages().execute("http://api.themoviedb.org/3/discover/movie?sort_" +
                            "by=popularity.desc&api_key=[ API Key]");
                    return true;
                case R.id.sort_top:
                    new fetchMovieImages().execute("http://api.themoviedb.org/3/discover/movie?sort_" +
                            "by=vote_average.desc&api_key=[ API Key]");
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }



    // Inflate our layout and attach adapter to GridView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.grid);
        //Adapter places images into the Gridview object
        gridAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(gridAdapter);
        gridview.setFriction((float).564545);

        //Query for data, API key is omitted on public repo
        // Source: https://www.themoviedb.org/documentation/api?language=en
        new fetchMovieImages().execute("http://api.themoviedb.org/3/discover/movie?sort_" +
                "by=popularity.desc&api_key=[ API Key]");
        return rootView;
    }
    //TODO onclickupdate
    // Parses data from query and updates View with adapter in a background thread
    public class fetchMovieImages extends AsyncTask<String, Void, String[]> {
        //JSON Parsing
        public String[] getSortedMoviesArray(String result) throws JSONException {
            String baseString = "http://image.tmdb.org/t/p/w500/";
            String[] finalList;
            JSONObject reader = new JSONObject(result);

            JSONArray topRatedMovies = reader.getJSONArray("results");

            finalList = new String[topRatedMovies.length()];


            for (int i = 0; i < topRatedMovies.length(); i++) {
                JSONObject movie = topRatedMovies.getJSONObject(i);
                finalList[i] = baseString + movie.getString("poster_path");
            }

            return finalList;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String result;
            InputStream incoming = null;
            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                incoming = connection.getInputStream();
                InputStreamReader is = new InputStreamReader(incoming);
                BufferedReader reader = new BufferedReader(is);
                StringBuilder buildData = new StringBuilder();

                if (incoming == null) {
                    return null;
                } else {
                    while ((result = reader.readLine()) != null) {
                        buildData.append(result + "/n");
                    }
                    result = buildData.toString();
                }

            } catch (IOException e) {
                return null;

            } finally {
                if (incoming != null) {
                    try {
                        incoming.close();
                    } catch (IOException e) {
                    }
                }
            }
            try {
                // send result to be parsed
                return getSortedMoviesArray(result);
            } catch (JSONException e) {

            }

            return null;
        }

        protected void onPostExecute(String[] result) {
            if(result == null)
                return;
            else {

                gridAdapter.clear();
                for (String movie : result) {
                    gridAdapter.add(movie);
                }
                //Update our View

                gridAdapter.notifyDataSetChanged();
            }

        }
    }

    //extends BaseAdapter to be able add images to Gridview

    //Populates our gridview with images
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> mThumbIds;

        public ImageAdapter(Context c) {
            mContext = c;
            mThumbIds = new ArrayList<String>();
        }

        public int getCount() {
            return mThumbIds.size();
        }

        public Object getItem(int position) {
            return mThumbIds.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        // Have Picasso handle image processing
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view;
            Log.v("image", "update image");
            if (convertView == null) {
                view = new ImageView(mContext);
                view.setAdjustViewBounds(true);

                view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setPadding(5, 5, 5, 5);
            } else {

                view = (ImageView) convertView;
            }


            Picasso.with(mContext).load(mThumbIds.get(position)).into(view);

            return view;
        }

        public void add(String result) {

            mThumbIds.add(result);
        }
        public void clear(){
            mThumbIds.clear();
        }


    }
}

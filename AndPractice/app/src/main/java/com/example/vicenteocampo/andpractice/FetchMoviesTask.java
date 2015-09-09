package com.example.vicenteocampo.andpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vicenteocampo.andpractice.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Vicente Ocampo on 8/30/2015.
 */
public class FetchMoviesTask extends AsyncTask<String,Void,Void> {

    Context mContext;


    public FetchMoviesTask(Context mContext){
            this.mContext = mContext;
    }

        //JSON Parsing and inserting  movie details to db
    public void getSortedMoviesArray(String result) throws JSONException {
            String baseString = "http://image.tmdb.org/t/p/w342/";
            String[] finalList;

            JSONObject reader = new JSONObject(result);
            JSONArray topRatedMovies = reader.getJSONArray("results");
            finalList = new String[topRatedMovies.length()];

            Cursor cFetch = mContext.getContentResolver().query(
                    MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            // Use contentprovider to insert the parsed data
            for (int i = 0; i < topRatedMovies.length(); i++) {
                    JSONObject movie = topRatedMovies.getJSONObject(i);
                    finalList[i] = baseString + movie.getString("poster_path");
                    if(!cFetch.moveToPosition(i)) {
                        ContentValues movieValues = new ContentValues();
                        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER, getPoster
                                (movie.getString("poster_path")));
                        movieValues.put(MovieContract.MovieEntry.COlUMN_SUMMARY, getPlot(movie.toString()));
                        movieValues.put(MovieContract.MovieEntry.COLUMN_INFO, getMovieInfo(movie.toString()));
                        mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,
                                movieValues);
                    }
                }

             cFetch.close();

    }

    public byte[] getPoster (String data) {

        String baseString = "http://image.tmdb.org/t/p/w342/";
        int result;
        byte[] finResult = null;
        InputStream  is ;
        Log.v("url",baseString + data);
        ByteArrayOutputStream oBuf = new ByteArrayOutputStream();
        try {
            //get poster image's bytearray to insert as blob in table
            URL url = new URL(baseString + data);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            is = connection.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(is);

            while((result = buf.read()) != -1) {
                oBuf.write(result);
            }
            finResult = oBuf.toByteArray();
            oBuf.close();

        } catch(IOException e){}


        return  finResult;
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
        // obtaining json data runs in an asynchronous thread
        @Override
        protected Void doInBackground(String... params) {
            String result =  null;
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
                Log.v("background","error in reading incoming json data");
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
                 getSortedMoviesArray(result);
            } catch (JSONException e) {

            }
            return null;
        }
    }





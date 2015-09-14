package com.example.vicenteocampo.andpractice;



import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;

import android.content.Loader;
import android.database.Cursor;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.vicenteocampo.andpractice.data.MovieContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    MovieAdapter gridAdapter;
    String source;
    String apiKey;
    private static final int LOADER_ID = 1;

    public MainActivityFragment() {

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("url",source);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // xml contains fragment with grid
        setHasOptionsMenu(true);
        //Query for data, API key is omitted on public repo
        // Source: https://www.themoviedb.org/documentation/api?language=en
        apiKey = "";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
            getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,null,null);
            switch(item.getItemId()){
                case R.id.sort_popular:
                    getActivity().getActionBar().setTitle("Pop Movies");
                    source = "http://api.themoviedb.org/3/discover/movie?sort_" +
                    "by=popularity.desc&api_key=" + apiKey;
                    onSettingChanged(source);
                    return true;
                case R.id.sort_top:
                    getActivity().getActionBar().setTitle("Top Rated Movies");
                    source = "http://api.themoviedb.org/3/discover/movie?sort_" +
                            "by=vote_average.desc&api_key=" + apiKey;
                    onSettingChanged(source);
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
        final GridView gridview = (GridView) rootView.findViewById(R.id.grid);
        //Adapter places images into the Gridview object
        // a new fetchMovies task is created
        source = "http://api.themoviedb.org/3/discover/movie?sort_" +
                "by=popularity.desc&api_key=" + apiKey;
        if(savedInstanceState != null)
            source = savedInstanceState.getString("url");

            gridAdapter = new MovieAdapter(getActivity(),null,0);
            gridview.setAdapter(gridAdapter);

            new FetchMoviesTask(getActivity()).execute(source);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Provide details for a specific movie to the detailsActivity through an intent
                Intent  details = new Intent(getActivity(), DetailsActivity.class);
                details.putExtra("id",position);
                startActivity(details);
            }
        });

        return rootView;
    }

    public void onSettingChanged(String source){
        new FetchMoviesTask(getActivity()).execute(source);
        getLoaderManager().restartLoader(LOADER_ID,null, this);

    }
    //handles the lifetime of out cursor used in out adapter
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),MovieContract.MovieEntry.CONTENT_URI, null,null,
                null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        gridAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        gridAdapter.swapCursor(null);
    }


}

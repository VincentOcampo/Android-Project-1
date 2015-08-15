package com.example.vicenteocampo.andpractice;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    // Create view for fragment and binds data from Picasso to gridview
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.grid);
        //Adapter places images into the Gridview object
        gridview.setAdapter(new ImageAdapter(getActivity()));
        //TODO
        new fetchMovieImages().execute("dsdsd");
        return rootView;
    }

    public class fetchMovieImages extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {




            return null;
        }

        protected void onPostExecute(String[] result) {}
    }
    //extends BaseAdapter to be able add images to Gridview





    public class ImageAdapter extends BaseAdapter{
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        // Have Picasso handle image processing
        public View getView(int position, View convertView, ViewGroup parent) {
             ImageView view = (ImageView) convertView;
             if (view == null) {
                view = new ImageView(mContext);
                 view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                         ViewGroup.LayoutParams.MATCH_PARENT));
                 view.setScaleType(ImageView.ScaleType.FIT_XY);
                 view.setPadding(1, 1, 1, 1);
             }else{
                 view = (ImageView) convertView;
             }


            Picasso.with(mContext).load(mThumbIds[position]).into(view);

            return view;
        }

        // references to our images
        private String[] mThumbIds = {
                "http://i.imgur.com/yRYXonm.jpg",
                "http://i.imgur.com/yRYXonm.jpg",
                "http://i.imgur.com/yRYXonm.jpg",
                "http://i.imgur.com/yRYXonm.jpg"
        };
    }
}

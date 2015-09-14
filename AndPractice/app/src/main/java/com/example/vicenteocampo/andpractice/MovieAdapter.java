package com.example.vicenteocampo.andpractice;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.vicenteocampo.andpractice.data.MovieContract;

/**
 * Created by Vicente Ocampo on 9/3/2015.
 */
public class MovieAdapter extends CursorAdapter {
    Context mContext;
    public MovieAdapter(Context context, Cursor c, int flags) {
        super(context,c,flags);
        mContext = context;

    }
    //create the type of view used in our gridview
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       ImageView view = new ImageView(mContext);
        view.setAdjustViewBounds(true);

        view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setPadding(10, 10, 10, 10);
        return view ;
    }
    // Bind data from our table to the created views using a cursor
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView view1 = (ImageView) view;
        Bitmap b;
        byte[] res = cursor.getBlob(cursor.getColumnIndex(MovieContract.
                MovieEntry.COLUMN_POSTER));
        if(res != null) {
            b = BitmapFactory.decodeByteArray(res, 0, res.length);
            view1.setImageBitmap(b);
        }
    }
}

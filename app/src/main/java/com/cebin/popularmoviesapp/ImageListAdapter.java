package com.cebin.popularmoviesapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by DanieleM on 23/04/2016.
 */
public class ImageListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] imageUrls;

    public ImageListAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.grid_view_fragment, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_view_fragment, parent, false);
        }

        ImageView imageView = (ImageView) convertView;

        if (TextUtils.isEmpty(imageUrls[position])) {
            //if imageUrls is empty
            Picasso.with(context).cancelRequest(imageView);
        } else {
            /* option 2: load placeholder with Picasso
            Picasso
                .with(context)
                .load(R.drawable.floorplan)
                .into(imageView);
                */



            Picasso.with(context).setIndicatorsEnabled(true);
            Picasso.with(context)
                    .load(imageUrls[position])
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(imageView);



            Log.d(ImageListAdapter.class.getSimpleName(), "getView: imgUrl[position] = "+imageUrls[position]);
            Log.d(ImageListAdapter.class.getSimpleName(), "getView: view position = "+position);
        }


        return convertView;
    }
}

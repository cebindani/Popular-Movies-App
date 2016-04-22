package com.cebin.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String base_url = "http://image.tmdb.org/t/p/";
        String imgSize = "w185";
        String poster = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


        ImageView imageView = (ImageView) findViewById(R.id.banner_imageView);

        Picasso.with(this)
                .setIndicatorsEnabled(true);
        Picasso.with(this)
                .load(base_url+imgSize+poster)
                .into(imageView);



    }
}

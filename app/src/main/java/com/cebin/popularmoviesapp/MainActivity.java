package com.cebin.popularmoviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

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

        String apiUrl = "http://api.themoviedb.org/3/movie/popular?api_key=171033484fca95820ee38d32ea548f25";

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
                new FetchMoviesData().execute(apiUrl);
        }
        else {
            Toast.makeText(MainActivity.this, "Ops... you appear to be offline!", Toast.LENGTH_SHORT).show();
        }





    }
}

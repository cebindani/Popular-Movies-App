package com.cebin.popularmoviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiUrl = "http://api.themoviedb.org/3/movie/popular?api_key=171033484fca95820ee38d32ea548f25";
        getMoviesDataFromAPI(apiUrl);

        String[] moviesPosters = {
                "http://i.imgur.com/rFLNqWI.jpg",
                "http://i.imgur.com/C9pBVt7.jpg",
                "http://i.imgur.com/rT5vXE1.jpg",
                "http://i.imgur.com/aIy5R2k.jpg",
                "http://i.imgur.com/MoJs9pT.jpg",
                "http://i.imgur.com/S963yEM.jpg",
                "http://i.imgur.com/rLR2cyc.jpg",
                "http://i.imgur.com/SEPdUIx.jpg",
                "http://i.imgur.com/aC9OjaM.jpg",
                "http://i.imgur.com/76Jfv9b.jpg",
                "http://i.imgur.com/fUX7EIB.jpg",
                "http://i.imgur.com/syELajx.jpg",
                "http://i.imgur.com/COzBnru.jpg",
                "http://i.imgur.com/Z3QjilA.jpg",
        };

        ListView listView = (ListView) findViewById(R.id.gridView);
        listView.setAdapter(new ImageListAdapter(MainActivity.this, moviesPosters));


    }


    private void getMoviesDataFromAPI(String apiUrl) {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FetchMoviesData().execute(apiUrl);
        } else {
            Toast.makeText(MainActivity.this, "Ops... you appear to be offline!", Toast.LENGTH_SHORT).show();
        }
    }


}

package com.cebin.popularmoviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAsyncTaskListener {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    GridView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (GridView) findViewById(R.id.gridView);
        //mListView.setAdapter(null);

        String apiUrl = "http://api.themoviedb.org/3/movie/popular?api_key=171033484fca95820ee38d32ea548f25";
        getMoviesDataFromAPI(apiUrl);

        final String[] moviesPosters = {
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





    }




    private void getMoviesDataFromAPI(String apiUrl) {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FetchMoviesData(this).execute(apiUrl);
        } else {
            Toast.makeText(MainActivity.this, "Ops... you appear to be offline!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSuccess(List<Movie> data) {

        //mListView = (ListView) findViewById(R.id.gridView);
        Log.d(LOG_TAG, "onSuccess: data.length = " + data.size());

        String[] postersPath = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            postersPath[i] = data.get(i).getPosterURL();
        }
        mListView.setAdapter(new ImageListAdapter(MainActivity.this, postersPath));

    }

    @Override
    public void onFailure() {

        Log.d(LOG_TAG, "onFailure: ");
    }
}

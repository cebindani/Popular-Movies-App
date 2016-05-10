package com.cebin.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAsyncTaskListener {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    GridView mGridView;
    List<Movie> listOfMovies = null;
    private String urlApiPath = "popular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gridView);


        getMoviesDataFromAPI(urlApiPath);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movieDetail = listOfMovies.get(position);
                Toast.makeText(view.getContext(), "You clicked on "+ movieDetail.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieDetail", movieDetail);
                startActivity(intent);



            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortby_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_popular: {
                urlApiPath = "popular";
                Log.i("Sort", "Sorting by popular films");
                getMoviesDataFromAPI(urlApiPath);
                return true;

            }
            case R.id.sort_top_rated: {
                urlApiPath = "top_rated";
                getMoviesDataFromAPI(urlApiPath);
                Log.i("Sort", "Sorting by top rated films");
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    private void getMoviesDataFromAPI(String sortBy) {

         //will be changed in Settings
        urlApiPath = sortBy;


        //Log.d(LOG_TAG, "getMoviesDataFromAPI: " + urlApiPath);
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && !networkInfo.getState().equals(NetworkInfo.DetailedState.VERIFYING_POOR_LINK)) {
            new FetchMoviesData(this).execute(urlApiPath);
        } else {
            Toast.makeText(MainActivity.this, "Ops... you appear to be offline!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSuccess(List<Movie> data) {

        this.listOfMovies = data;
        //Log.d(LOG_TAG, "onSuccess: data.length = " + listOfMovies.size());


        String[] postersPath = new String[listOfMovies.size()];
        for (int i = 0; i < listOfMovies.size(); i++) {
            postersPath[i] = listOfMovies.get(i).getPosterPath();
        }
        mGridView.setAdapter(new ImageListAdapter(MainActivity.this, postersPath));

    }

    @Override
    public void onFailure() {

        Toast.makeText(MainActivity.this, "Ops...something was wrong", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onFailure: ");
    }


    public int getImageSize() {


       // Log.d(LOG_TAG, "xdpi: " + this.getResources().getDisplayMetrics().xdpi);

        int width = (int) this.getResources().getDisplayMetrics().xdpi;


        String widthPath;
        if (width <= 92)
            widthPath = "/w92";
        else if (width <= 154)
            widthPath = "/w154";
        else if (width <= 185)
            widthPath = "/w185";
        else if (width <= 342)
            widthPath = "/w342";
        else if (width <= 500)
            widthPath = "/w500";
        else
            widthPath = "/w780";
        Log.d(getClass().toString(), "imgSize: " + width + "\n widthPath: "+widthPath);

        return width/2;

    }


}

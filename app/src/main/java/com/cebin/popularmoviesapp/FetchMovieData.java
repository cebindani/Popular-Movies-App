package com.cebin.popularmoviesapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanieleM on 22/04/2016.
 */
class FetchMoviesData extends AsyncTask<String, Void, String[]> {

    private static final String LOG_TAG = FetchMoviesData.class.getSimpleName();

    @Override
    protected String[] doInBackground(String... params) {


        try {
            fetchData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new String[0];
    }


    private List<Movie> fetchData(String apiUrl) throws IOException {


        URL url = new URL(apiUrl);

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                //stream was empty. Nothing to parse
                return null;
            }
            moviesJsonStr = buffer.toString();
            Log.d(LOG_TAG, "fetchData: " + moviesJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream ", e);
                }
            }

        }

        try {
            return getMovieDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;


    }

    private List<Movie> getMovieDataFromJson(String moviesJsonStr) throws JSONException {


        JSONObject moviesJsonObject = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJsonObject.getJSONArray("results");

        String[] posters = null;
        List<Movie> movieList = new ArrayList<Movie>();
        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject moviesData = moviesArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.poster_path = moviesData.getString("poster_path");
            movie.id = moviesData.getLong("id");
            Log.d(LOG_TAG, "poster_path = " + movie.poster_path);

            movieList.add(i, movie);

        }


        //String movie_poster_path = moviesJson.getString("poster_path");


        return movieList;


    }


}

package com.cebin.popularmoviesapp;

import android.net.Uri;
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
public class FetchMoviesData extends AsyncTask<String, MyAsyncTaskListener, List<Movie>> {

    private static final String LOG_TAG = FetchMoviesData.class.getSimpleName();
    HttpURLConnection urlConnection = null;
    private MyAsyncTaskListener myAsyncTaskListener;


    public FetchMoviesData(MyAsyncTaskListener myAsyncTaskListener) {
        this.myAsyncTaskListener = myAsyncTaskListener;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {

        myAsyncTaskListener.onSuccess(movieList); //getPosterURL(strings)

    }

    @Override
    protected List<Movie> doInBackground(String... params) {


        try {
            return fetchMoviesDataFromUrl(params[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onCancelled() {
        urlConnection.disconnect();
    }

    private List<Movie> fetchMoviesDataFromUrl(String apiUrl) throws IOException {


        URL url = new URL(apiUrl);


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
            //Log.d(LOG_TAG, "fetchMoviesDataFromUrl: " + moviesJsonStr);

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
            return getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;


    }

    private List<Movie> getMoviesDataFromJson(String moviesJsonStr) throws JSONException {


        JSONObject moviesJsonObject = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJsonObject.getJSONArray("results");

        String[] pathArray = new String[moviesArray.length()];
        List<Movie> moviesList = new ArrayList<Movie>();
        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject moviesData = moviesArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.posterPath = moviesData.getString("poster_path");
            movie.id = moviesData.getLong("id");
            //Log.d(LOG_TAG, "poster_path = " + movie.posterPath);

            moviesList.add(i, movie);

            pathArray[i] = movie.posterPath;
        }

        //retornando pathArray, mas o ideal seria retornar a lista de Movies
        return moviesList;
    }


    private String[] getPosterURL(String[] pathArray){

        String base_url = "image.tmdb.org/t/p";
        String imgSize = "w185";
        //String poster = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        String[] postersUrl = new String[pathArray.length];
        Uri.Builder builder = new Uri.Builder();

        for (int i = 0; i < pathArray.length; i++) {

            builder.scheme("http").encodedAuthority(base_url).path(imgSize).appendEncodedPath(pathArray[i]).build();

            //Uri.parse(base_url).buildUpon().path(imgSize).appendEncodedPath(pathArray[i]).build();
            postersUrl[i] = builder.toString();
        }
        return postersUrl;

    }


}

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
import java.net.MalformedURLException;
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


        if (movieList != null) {
            myAsyncTaskListener.onSuccess(movieList); //generatePosterURL(strings)
        } else {
            myAsyncTaskListener.onFailure();
        }

    }

    @Override
    protected List<Movie> doInBackground(String... params) {


        try {

            return fetchMoviesDataFromUrl(mountApiUrl(params[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onCancelled() {
        urlConnection.disconnect();
    }


    private URL mountApiUrl(String path) {
        // "http://api.themoviedb.org/3/movie/popular?api_key=171033484fca95820ee38d32ea548f25"
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("api.themoviedb.org/3")
                .appendEncodedPath("movie")
                .appendPath(path)
                .appendQueryParameter("api_key", "171033484fca95820ee38d32ea548f25")
                .build();
        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "mountApiUrl: ", e);
        }

        return url;
    }

    private List<Movie> fetchMoviesDataFromUrl(URL apiUrl) throws IOException {


        Log.d(LOG_TAG, "fetchMoviesDataFromUrl: "+apiUrl);
        InputStream inputStream;
        BufferedReader reader = null;
        String moviesJsonStr = null;


        try {
            urlConnection = (HttpURLConnection) apiUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(4000);
            urlConnection.connect();



            inputStream = urlConnection.getInputStream();
            //inputStream = null;
            if (inputStream == null) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();

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
            return (moviesJsonStr != null) ? getMoviesDataFromJson(moviesJsonStr) : null;
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
        List<Movie> moviesList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject moviesData = moviesArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setPosterPath(moviesData.getString("poster_path"));
            movie.setId(moviesData.getLong("id"));
            movie.setOriginalTitle(moviesData.getString("original_title"));
            movie.setBackdropPath(moviesData.getString("backdrop_path"));
            movie.setSynopsis(moviesData.getString("overview"));
            movie.setUserRating(moviesData.getDouble("vote_average"));
            movie.setReleaseDate(moviesData.getString("release_date"));
            moviesList.add(i, movie);

            //pathArray[i] = movie.posterPath;
        }

        //retornando pathArray, mas o ideal seria retornar a lista de Movies
        return moviesList;
    }


}

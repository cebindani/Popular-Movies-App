package com.cebin.popularmoviesapp;

import android.net.Uri;

/**
 * Created by DanieleM on 23/04/2016.
 */
public class Movie {

    long id;
    String posterPath = null;
    String originalTitle = null;
    String posterThumbnail = null;
    String synopsis = null; //overview
    Double userRating = 0.0; //vote_average
    String releaseDate = null;

    public Movie() {
        super();
    }


    public String getPosterURL() {

        String base_url = "image.tmdb.org/t/p";
        String imgSize = "w185";
        //String poster = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").encodedAuthority(base_url).path(imgSize).appendEncodedPath(posterPath).build();

        //Uri.parse(base_url).buildUpon().path(imgSize).appendEncodedPath(pathArray[i]).build();
        String posterUrl = builder.toString();

        return posterUrl;

    }


}

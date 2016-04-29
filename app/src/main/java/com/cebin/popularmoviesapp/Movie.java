package com.cebin.popularmoviesapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DanieleM on 23/04/2016.
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
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


    public Movie(Parcel in) {
        id = in.readLong();
        originalTitle = in.readString();
        posterThumbnail = in.readString();
        synopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(originalTitle);
        dest.writeString(posterThumbnail);
        dest.writeString(synopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);

    }
}

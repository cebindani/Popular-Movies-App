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
    private long id;
    private String posterPath = null;
    private String originalTitle = null;
    private String posterThumbnail = null;
    private String synopsis = null; //overview
    private Double userRating = 0.0; //vote_average
    private String releaseDate = null;


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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = generatePosterURL(posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public void setPosterThumbnail(String posterThumbnail) {
        this.posterThumbnail = generatePosterURL(posterThumbnail);
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String generatePosterURL(String imagePath) {

        String base_url = "image.tmdb.org/t/p";
        String imgSize = "w185";
        //String poster = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").encodedAuthority(base_url).path(imgSize).appendEncodedPath(imagePath).build();

        //Uri.parse(base_url).buildUpon().path(imgSize).appendEncodedPath(pathArray[i]).build();
        String posterUrl = builder.toString();

        return posterUrl;

    }
}

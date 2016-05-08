package com.cebin.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movieDetail");

        ImageView thumbnailImageView = (ImageView) findViewById(R.id.poster_thumbnail);
        TextView originalTitleTextView = (TextView) findViewById(R.id.original_title);
        TextView synopsisTextView = (TextView) findViewById(R.id.synopsis);
        TextView ratingTextView = (TextView) findViewById(R.id.user_rating);
        TextView releaseDateTextView = (TextView) findViewById(R.id.release_date);

        if (intent.hasExtra("movieDetail")) {
            //imagem.setImageResource();
            Picasso.with(context)
                    .load(movie.getPosterThumbnail())
                    .into(thumbnailImageView);

            originalTitleTextView.setText(movie.getOriginalTitle());
            synopsisTextView.setText(movie.getSynopsis());
            ratingTextView.setText(movie.getUserRating().toString());


            SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {

                Date date = stringToDate.parse(movie.getReleaseDate());
                stringToDate = new SimpleDateFormat("dd/MM/yyyy");
                releaseDateTextView.setText(stringToDate.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

}

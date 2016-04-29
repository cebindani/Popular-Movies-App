package com.cebin.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        ImageView imagem = (ImageView) findViewById(R.id.poster_thumbnail);
        TextView originalTitleTextView = (TextView) findViewById(R.id.original_title);
        TextView synopsisTextView = (TextView) findViewById(R.id.synopsis);
        TextView ratingTextView = (TextView) findViewById(R.id.user_rating);
        TextView releaseDateTextView = (TextView) findViewById(R.id.release_date);

        if (intent.hasExtra("movieDetail")) {
            //imagem.setImageResource();
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w500/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg")
                    .into(imagem);

            originalTitleTextView.setText(movie.originalTitle);
            synopsisTextView.setText(movie.synopsis);
            ratingTextView.setText(movie.userRating.toString());
            releaseDateTextView.setText(movie.releaseDate);
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

package com.cebin.popularmoviesapp;

import java.util.List;

/**
 * Created by DanieleM on 24/04/2016.
 */
public interface MyAsyncTaskListener {
    void onSuccess(List<Movie> data);
    void onFailure();


}

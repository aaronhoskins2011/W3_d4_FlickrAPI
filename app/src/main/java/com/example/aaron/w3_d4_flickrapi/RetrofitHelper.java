package com.example.aaron.w3_d4_flickrapi;

import android.util.Log;

import com.example.aaron.w3_d4_flickrapi.model.FlickrProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aaron on 8/17/17.
 */

public class RetrofitHelper {
    public static final String FLICKR_URL = "http://api.flickr.com/";
    public static final String PATH = "services/feeds/photos_public.gne";
    public static final String QUERY = "kitten&format=json&nojsoncallback=1";
    public static Retrofit create(){
        //url = "";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(FLICKR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Log.d("RETROHELP", "create: " + retrofit.toString());
        return retrofit;
    }

    public static Observable<FlickrProfile> getFlickrObservable(){
        Retrofit retrofit = create();

        FlickrService flickrservice = retrofit.create(FlickrService.class);
        Log.d("RETROHELP", "getFlickrObservable: " + flickrservice.getObservable(QUERY).toString());
        return flickrservice.getObservable(QUERY);
    }

    public interface FlickrService {
        @GET(PATH)
        Observable<FlickrProfile> getObservable(@Query("tag") String query);
    }
}

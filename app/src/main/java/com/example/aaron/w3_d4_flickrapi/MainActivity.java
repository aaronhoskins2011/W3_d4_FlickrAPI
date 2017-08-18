package com.example.aaron.w3_d4_flickrapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.aaron.w3_d4_flickrapi.model.FlickrProfile;
import com.example.aaron.w3_d4_flickrapi.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    static FlickrProfile flickrProfile;
    public static final String FLICKR_URL = "http://api.flickr.com/services/feeds/photos_public.gne?tag=kitten&format=json&nojsoncallback=1";
    static String responseString = "";
    List<Item> items = new ArrayList<>();
    RecyclerView rvFlickrList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    FlickrListAdaptor flickrListAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*************************************
         **   Client/Request initialization **
          * *********************************/
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(FLICKR_URL).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // Type list = new TypeToken<List<FlickrProfile>>(){}.getType();
                FlickrProfile temp;

                Gson gson = new Gson();
                responseString = response.body().string();
                temp = gson.fromJson(responseString, FlickrProfile.class);
                items = temp.getItems();

                Log.d("ACTIVITY2", "onResponse: " + items.get(0).getLink());
                initRV();


            }
        });



    }

    private void initRV(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*************************************
                 **    Set Up Recycle View           **
                 * **********************************/
                rvFlickrList = (RecyclerView)findViewById(R.id.rvFlickrList);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                itemAnimator = new DefaultItemAnimator();
                rvFlickrList.setLayoutManager(layoutManager);
                rvFlickrList.setItemAnimator(itemAnimator);
                flickrListAdaptor = new FlickrListAdaptor(items, getApplicationContext());
                rvFlickrList.setAdapter(flickrListAdaptor);
                flickrListAdaptor.notifyDataSetChanged();
            }
        });
    }
}

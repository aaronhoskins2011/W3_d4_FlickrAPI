package com.example.aaron.w3_d4_flickrapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.aaron.w3_d4_flickrapi.model.FlickrProfile;
import com.example.aaron.w3_d4_flickrapi.model.Item;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


/**
 * Created by aaron on 8/17/17.
 */

public class FlickrListAdaptor extends RecyclerView.Adapter<FlickrListAdaptor.ViewHolder>{
    List<Item> item = new ArrayList<>();
    ImageView imgFromFlickr;
    Context context;

    public FlickrListAdaptor(List<Item> item, Context context) {
        this.item = item;
        this.context = context;

    }

    @Override
    public FlickrListAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_item_layout,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrListAdaptor.ViewHolder holder, int position) {
        final Item imageItem = item.get(position);
        String imageUrl = imageItem.getLink();
        Log.d("TAG", "onBindViewHolder: " + imageUrl);
        Glide.with(context.getApplicationContext())
                .load(imageUrl)
                .asBitmap()
                .into(new BitmapImageViewTarget((ImageView)imgFromFlickr) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            imgFromFlickr = (ImageView)itemView.findViewById(R.id.imgFlickrThumbnail);


        }

    }
}

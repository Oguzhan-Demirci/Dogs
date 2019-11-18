package com.rl.dogs.View;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rl.dogs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    List<String> photoURLs = new ArrayList<>();

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoHolder(view);
    }

    public void setPhotoURLs(List<String> photoURLs) {
        this.photoURLs = photoURLs;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        if (!photoURLs.isEmpty())
            Picasso.get().load(photoURLs.get(position)).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return photoURLs.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder{

        ImageView iv;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_photo);
        }
    }
}

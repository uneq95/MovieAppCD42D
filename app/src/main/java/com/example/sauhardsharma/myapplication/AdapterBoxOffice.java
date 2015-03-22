package com.example.sauhardsharma.myapplication;

import android.content.Context;
import android.graphics.Movie;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.sauhardsharma.myapplication.VolleySingleton;

import static android.support.v7.widget.RecyclerView.Adapter;

/**
 * Created by nairit on 22/3/15.
 */
public class AdapterBoxOffice extends Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
    private ArrayList<HollyMovie> listMovies=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private ImageLoader imageLoader;
    private Object title;

    public AdapterBoxOffice(Context context){
        layoutInflater=LayoutInflater.from(context);
        volleySingleton=VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();

    }
    public void setMovies(ArrayList<HollyMovie> listMovies){
        this.listMovies=listMovies;
        notifyItemRangeChanged(0,listMovies.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=layoutInflater.inflate(R.layout.custom_movie_box_office,parent,false);

        ViewHolderBoxOffice viewHolder=new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final ViewHolderBoxOffice holder, int position) {
        HollyMovie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        holder.movieReleaseDate.setText(currentMovie.getReleaseDateTheater().toString());
        holder.movieAudienceScore.setRating(currentMovie.getAudienceScore()/20.0F);
        String urlThumnail=currentMovie.getUrlThumbnail();

        if(urlThumnail!=null){
            imageLoader.get(urlThumnail,new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean immediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }






    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{
        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;





        public ViewHolderBoxOffice(View itemView){
            super(itemView);
            movieThumbnail= (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle= (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate= (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore= (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }

    }
}

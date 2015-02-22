package com.example.sauhardsharma.myapplication;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{

	public final Context context;
	List<RowItem> movies;
	public final String[] url;
	public final int[] imdbRatingsArray,rottenTomatoesRatingArray;
	
	public CustomListAdapter(Context context,List<RowItem> moviesList,String[] url,int[]imdbRatingArray,int[] rtRatingArray){
		this.context=context;
		this.movies=moviesList;
		this.url=url;
		this.imdbRatingsArray=imdbRatingArray;
		this.rottenTomatoesRatingArray=rtRatingArray;
		
	}
	private class ViewHolder{
		ImageView movieThumbnail;
		TextView movieTitle,imdbRating,rottenTomatoesRating;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movies.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movies.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return movies.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			
			convertView=inflater.inflate(R.layout.custom_list_layout, parent, false);
			holder=new ViewHolder();
			holder.movieThumbnail=(ImageView)convertView.findViewById(R.id.iv_movie_poster);
			holder.movieTitle=(TextView)convertView.findViewById(R.id.tv_movie_title);
			holder.imdbRating=(TextView)convertView.findViewById(R.id.tv_movie_imdb_rating);
			holder.rottenTomatoesRating=(TextView)convertView.findViewById(R.id.tv_movie_imdb_rating);
			convertView.setTag(holder);
		}
		RowItem item=(RowItem)getItem(position);
		//Add Picasso Library and load movieThumbnail with it
		//Picasso.with(context).load(ThumbUrl[position]).into(holder.movieThumbnail);
		holder.movieTitle.setText(item.getMovieTitle());
		holder.imdbRating.setText(item.getImdbRating());
		holder.rottenTomatoesRating.setText(item.getRottenTomatoesRating());
		return convertView;
	}

}

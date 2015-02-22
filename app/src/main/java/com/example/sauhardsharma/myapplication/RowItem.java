package com.example.sauhardsharma.myapplication;

public class RowItem {

	String thumbUrl,movieTitle;
	int imdbRating,rottenTomatoesRating;
	
	public RowItem(String thumUrl,String movieTitle,int imdbRating,int rottenTomatoesRating){
		this.thumbUrl=thumUrl;
		this.movieTitle=movieTitle;
		this.imdbRating=imdbRating;
	    this.rottenTomatoesRating=rottenTomatoesRating;
	}
	public String getMovieTitle(){
		return movieTitle;
	}
	public String getThumbUrl(){
		return thumbUrl;
	}
	public int getImdbRating(){
		return imdbRating;
	}
	public int getRottenTomatoesRating(){
		return rottenTomatoesRating;
	}
	public void setMovieTitle(String title){
		this.movieTitle=title;
	}
	public void setThumbUrl(String url){
		this.thumbUrl=url;
	}
	public void setImdbRating(int rating){
		this.imdbRating=rating;
	}
	public void setRottenTomatoesRating(int rating){
		 this.rottenTomatoesRating=rating;
	}
	
}

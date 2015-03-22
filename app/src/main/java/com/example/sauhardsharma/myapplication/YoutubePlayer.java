package com.example.sauhardsharma.myapplication;

//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.util.ArrayList;
import java.util.List;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;


import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
//import android.content.res.Configuration;
import android.os.Bundle;
//import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
//import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
//import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.OnFullscreenListener {

	
	public static final String API_KEY ="AIzaSyAWy_ytEHNJnJB5xeLDwHi1ndB3mYuqq0k";
	String VIDEO_ID	;
	String[] vidList;
    String[] videoId;
    String[] ThumbnailsUrl;
    List<RowItem> rowItem;
    YouTubePlayer youtubeplayer;
    YouTubePlayerView ytpView ;
    TextView tvMovieTrailerName;
    LinearLayout baseLayout;
    View otherViews;
	private boolean isFullscreen;
	private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
			: ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    String movieName;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ytp);
		ytpView = (YouTubePlayerView)findViewById(R.id.ytpView);
		baseLayout=(LinearLayout)findViewById(R.id.layout);
        tvMovieTrailerName=(TextView)findViewById(R.id.tvTrailerMovieName);
		otherViews = findViewById(R.id.otherViews);
        ytpView.initialize(API_KEY, this);
		Intent i=getIntent();
        VIDEO_ID=i.getStringExtra("YoutubeId");
        movieName=i.getStringExtra("movieNameTrailer");
        tvMovieTrailerName.setText(movieName+"");
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		doLayout();
		
	}
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Failed to Initialize!", Toast.LENGTH_LONG).show();
		
	}
	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer youtubeplayer,
			boolean wasRestored) {
		// TODO Auto-generated method stub
		this.youtubeplayer=youtubeplayer;
		//setControlsEnabled();
	    // Specify that we want to handle fullscreen behavior ourselves.
	    youtubeplayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
	    youtubeplayer.setOnFullscreenListener(this);
	    setRequestedOrientation(PORTRAIT_ORIENTATION);
		int controlFlags = youtubeplayer.getFullscreenControlFlags();
		controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
		if (!wasRestored) {
			youtubeplayer.loadVideo(VIDEO_ID);
		}
		youtubeplayer.setFullscreenControlFlags(controlFlags);
		
		Toast.makeText(this, "Successfully Initialized!", Toast.LENGTH_LONG).show();
		
	}

	@Override
	//
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		doLayout();
	}

	@Override
	public void onFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;

		doLayout();

		// TODO Auto-generated method stub

	}

	private void doLayout() {
		LayoutParams playerParams = (LayoutParams) ytpView
				.getLayoutParams();
		if (isFullscreen) {
			// When in fullscreen, the visibility of all other views than the
			// player should be set to
			// GONE and the player should be laid out across the whole screen.
			playerParams.width = LayoutParams.MATCH_PARENT;
			playerParams.height = LayoutParams.MATCH_PARENT;

			otherViews.setVisibility(View.GONE);
		} else {
			// This layout is up to you - this is just a simple example
			// (vertically stacked boxes in
			// portrait, horizontally stacked in landscape).
			otherViews.setVisibility(View.VISIBLE);
			ViewGroup.LayoutParams otherViewsParams = otherViews
					.getLayoutParams();

			playerParams.width = otherViewsParams.width = MATCH_PARENT;
			playerParams.height = WRAP_CONTENT;
			playerParams.weight = 0;
			otherViewsParams.height = 0;
			baseLayout.setOrientation(LinearLayout.VERTICAL);

		}
	}
	
	
}


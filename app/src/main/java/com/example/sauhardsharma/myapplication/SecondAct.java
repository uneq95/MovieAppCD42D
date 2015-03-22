package com.example.sauhardsharma.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ritesh_kumar on 21-Mar-15.
 */
public class SecondAct extends Activity {
    ScrollView visibleLayout;
    String movieName="";
    TextView tvYear,tvimdbRating,tvTitle,tvDirector,tvPlot,tvLang,tvRdate,tvActor,tvCountry,tvrottenRating;
    DownloadChannelList download;
    String omdbRequestLink;
    Database_movie movieDb;
    Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details );
        //get selected movie name from previous activity here
        Intent i=getIntent();
        movieName=i.getStringExtra("SelectedMovie");

        //initialise views
        initView();
        visibleLayout=(ScrollView)findViewById(R.id.scrollView_movie_details);
        visibleLayout.setVisibility(View.INVISIBLE);
        //check network connectivity
         if(isNetworkConnected()){
            DownloadChannelList download =new DownloadChannelList(this);
            download.execute();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        }else{
            LinearLayout ytpLayout=(LinearLayout)findViewById(R.id.ytpchannelLayout);
            ytpLayout.setVisibility(View.INVISIBLE);

            Toast.makeText(getBaseContext(), "No Internet Access!",  Toast.LENGTH_LONG).show();
        }

//        download =new DownloadChannelList(this);
//        download.execute();
    }
    public void onClickAddToWatchList(View v){
        try{
            movieDb.open();
            movieDb.insertDetails(movieName, omdbRequestLink);
            Toast.makeText(getBaseContext(),movieName+" successfully added to your wishlist",Toast.LENGTH_SHORT).show();
            movieDb.close();
        }catch(SQLException e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(),"Error adding to wishlist! Please try again!!",Toast.LENGTH_SHORT).show();
        }

    }
    public void onClickWatchTrailer(View v){

        LoadingAlertDialog();
        DownloadTrailerLink trailerDownload=new DownloadTrailerLink(this,movieName);
        trailerDownload.execute();
    }

    public void initView(){
        movieDb=new Database_movie(this);
        omdbRequestLink="http://www.omdbapi.com/?t="+movieName;
        tvYear=(TextView)findViewById(R.id.tvYEAR);
        tvimdbRating=(TextView)findViewById(R.id.tvimdbRATING);
        tvTitle=(TextView)findViewById(R.id.tvNAME);
        tvDirector=(TextView)findViewById(R.id.tvDIRECT);
        tvPlot=(TextView)findViewById(R.id.tvplot);
        tvLang=(TextView)findViewById(R.id.tvLANG);
        tvRdate=(TextView)findViewById(R.id.tvRDATE);
        tvActor=(TextView)findViewById(R.id.tvACTORS);
        tvCountry=(TextView)findViewById(R.id.tvCOUNTRY);
        tvrottenRating=(TextView)findViewById(R.id.tvRTRATINGRATING);
    }
    boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo()!=null);

    }
    class DownloadChannelList extends AsyncTask<Void, Void,Void> {
        String year,imdbRating,title,director,plot,lang,rdate,actor,country,poster,rtRating;
        Context context;

        public DownloadChannelList(Context c) {
            // TODO Auto-generated constructor stub

            this.context=c;
        }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                HttpClient client = new DefaultHttpClient();
                Log.d("http", "http request set");
                HttpUriRequest request = new HttpGet(omdbRequestLink);
                HttpResponse response = client.execute(request);
                Log.d("http response","http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject jsonobj = new JSONObject(result);
                title = jsonobj.getString("Title");
                year = jsonobj.getString("Year");
                imdbRating = jsonobj.getString("imdbRating");
                director = jsonobj.getString("Director");
                plot = jsonobj.getString("Plot");
                lang = jsonobj.getString("Language");
                rdate = jsonobj.getString("Released");
                actor = jsonobj.getString("Actors");
                country = jsonobj.getString("Country");
                poster = jsonobj.getString("Poster");
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            try {
                    LinearLayout ll =(LinearLayout)((Activity) context).findViewById(R.id.acttwo_hidingLayout);
                    ll.setVisibility(View.GONE);
                    visibleLayout.setVisibility(View.VISIBLE);
                System.out.println("layout visible");
                    tvYear.setText(year+"");
                    tvimdbRating.setText(imdbRating+"");
                    tvTitle.setText(title+"");
                    tvDirector.setText(director+"");
                    tvPlot.setText(plot+"");
                    tvLang.setText(lang+"");
                    tvRdate.setText(rdate+"");
                    tvActor.setText(actor+"");
                    tvCountry.setText(country+"");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                super.onPostExecute(result);
            } catch (Exception e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }



    class DownloadTrailerLink extends AsyncTask<Void, Void,Void> {
        String movie="";
        Context context;
        String youtubeLink="";

        public DownloadTrailerLink(Context c,String movie) {
            // TODO Auto-generated constructor stub
            this.movie=movie;
            this.context=c;
        }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                HttpClient client = new DefaultHttpClient();
                Log.d("http", "http request set");
                HttpUriRequest request = new HttpGet("https://gdata.youtube.com/feeds/api/videos?q="+movie+"-trailer&start-index=1&max-results=1&v=2&alt=json");
                HttpResponse response = client.execute(request);
                Log.d("http response","http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject jsonobj = new JSONObject(result);
                final JSONObject jsonObject = jsonobj.getJSONObject("feed").getJSONArray("entry").getJSONObject(0  ).getJSONObject("content");
                youtubeLink=jsonObject.getString("src");
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            // dismiss alert dialog here

            /*.............................*/

            System.out.println(youtubeLink);
            if (youtubeLink.length() > 15)

            {
                int a = youtubeLink.indexOf("youtube.com"), in;
                Toast.makeText(getBaseContext(), youtubeLink, Toast.LENGTH_SHORT).show();
                for (in = a+11; in < youtubeLink.length(); in++) {
                    if (youtubeLink.charAt(in) == '?')
                        break;
                }

                String YoutubeId = youtubeLink.substring(a + 14, in );

                Toast.makeText(getBaseContext(), YoutubeId, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), YoutubePlayer.class);
                //i.putExtra("MovieTrailerLink",youtubeLink);
                i.putExtra("YoutubeId", YoutubeId);
                startActivity(i);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                super.onPostExecute(result);
            }
        }


    }

    public void LoadingAlertDialog(){
         dialog= new AlertDialog.Builder(this);
         ProgressBar pb=new ProgressBar(this);
         TextView tvLoading= new TextView(this);
         tvLoading.setText("Loading...");
         LinearLayout ll= new LinearLayout(this);
         ll.addView(pb);
         ll.addView(tvLoading);
         dialog.setView(ll);
         dialog.setCancelable(false);
         dialog.create();
         dialog.show();

    }
}

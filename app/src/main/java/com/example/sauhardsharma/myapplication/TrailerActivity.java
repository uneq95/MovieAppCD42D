package com.example.sauhardsharma.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.ArrayList;


public class TrailerActivity extends ActionBarActivity {

    String movieName="";
    EditText movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ytpchannel);
       // movie=(EditText)findViewById(R.id.etmovie);
        //getTrailerLink();
//        if(isNetworkConnected()){
//            DownloadChannelList download =new DownloadChannelList(this);
//            download.execute();
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//
//        }else{
//            LinearLayout ytpLayout=(LinearLayout)findViewById(R.id.ytpchannelLayout);
//            ytpLayout.setVisibility(View.GONE);
//
//            Toast.makeText(getBaseContext(), "No Internet Access!",  Toast.LENGTH_LONG).show();
//        }
        DownloadChannelList download =new DownloadChannelList(this);
        download.execute();
    }

    class DownloadChannelList extends AsyncTask<Void, Void,Void> {
        String ytdLink;
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
               HttpUriRequest request = new HttpGet("https://gdata.youtube.com/feeds/api/videos?q=3idiots-trailer&start-index=1&max-results=1&v=2&alt=json");
                //HttpUriRequest request = new HttpGet("https://gdata.youtube.com/feeds/api/videos?author=technexiitbhu&v=2&alt=jsonc");
               // HttpUriRequest request = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=uf8n7u4q68rma9aymq2dzufc");
                HttpResponse response = client.execute(request);
                System.out.println(response);
                Log.d("http response","http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                Log.d("json object", "going to initialize object");
                JSONObject json = new JSONObject(result);
                final JSONObject jsonObject = json.getJSONObject("feed").getJSONArray("entry").getJSONObject(0  ).getJSONObject("content");
                //final JSONArray jsonArray = json.getJSONObject("data").getJSONArray("items");
                ytdLink=jsonObject.getString("src");
                System.out.println(ytdLink);
                //Toast.makeText(getBaseContext(),ytdLink,Toast.LENGTH_SHORT).show();
                //now send this link to youtube player and play trailer

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
                LinearLayout ll =(LinearLayout)((Activity) context).findViewById(R.id.progressLayout);
                ll.setVisibility(View.GONE);

                Toast.makeText(getBaseContext(),ytdLink,Toast.LENGTH_SHORT).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                super.onPostExecute(result);
            } catch (Exception e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void onClickGo(View v){
       //String y= getTrailerLink(movie.getText().toString());
        //Toast.makeText(getBaseContext(),"url = "+y,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo()!=null);

    }
}

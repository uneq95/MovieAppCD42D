package com.example.sauhardsharma.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FirstPageRt extends ActionBarActivity {

    ListView movieList;
    String moviename="titanic";
    int[] imdbRatings,rtRatings;
    String[] movieTitles,thumbnailUrl;
    List<RowItem> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);
        movieList =(ListView)findViewById(R.id.listView);

        if(isNetworkConnected()){
            DownloadChannelList download =new DownloadChannelList(this);
            download.execute();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        }else{
//            LinearLayout ytpLayout=(LinearLayout)findViewById(R.id.ytpchannelLayout);
//            ytpLayout.setVisibility(View.GONE);
            ProgressBar pg=(ProgressBar)findViewById(R.id.progressBar);
            pg.setVisibility(View.GONE);

            Toast.makeText(getBaseContext(),"Please connect to Internet",Toast.LENGTH_LONG).show();
        }
    }

    class DownloadChannelList extends AsyncTask<Void, Void,Void> {

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
                HttpUriRequest request = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=uf8n7u4q68rma9aymq2dzufc");
                //HttpUriRequest request = new HttpGet("https://gdata.youtube.com/feeds/api/videos?author=technexiitbhu&v=2&alt=jsonc");
                HttpResponse response = client.execute(request);
                Log.d("http response","http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject json = new JSONObject(result);
                final JSONArray jsonArray = json.getJSONArray("items");
                //final JSONArray jsonArray = json.getJSONObject("data").getJSONArray("items");
                int length= jsonArray.length();
                String [] array = new String[length];
                String[] channelId = new String[length] ;
                String[] thumbId = new String[length];

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //get movie title from here
                    String title = jsonObject.getString("title");
                    array[i]=title;

                    String id = jsonObject.getString("id");
                    channelId[i]=id;
                    String url = jsonObject.getJSONObject("thumbnail").getString("sqDefault");
                    thumbId[i]=url;
                }
//                vidList=array.clone();
//                videoId=channelId.clone();
                thumbnailUrl=thumbId.clone();
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
//                LinearLayout ll =(LinearLayout)((Activity) context).findViewById(R.id.progressBar);
//                ll.setVisibility(View.GONE);
                ProgressBar p=(ProgressBar)((Activity)context).findViewById(R.id.progressBar);
                rowItems = new ArrayList<RowItem>();
//                for(int i=0;i<vidList.length;i++){
//                    RowItem item =new RowItem(vidList[i], videoId[i], thumbnailUrl[i]);
//                    rowItem.add(item);
//                }

              ///  CustomListAdapter listAdapter= new CustomListAdapter(YtpChannel.this, rowItem,thumbnailUrl);
              //  movieList.setAdapter(listAdapter);
                movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position,
                                            long id) {
                        // TODO Auto-generated method stub
//                        Toast.makeText(getBaseContext(), "video id " + position + "=" + videoId[position], Toast.LENGTH_SHORT).show();
//                        Intent intentToPlayer= new Intent(YtpChannel.this,YoutubePlayer.class);
//                        intentToPlayer.putExtra("selected video id",videoId[position]);
//                        intentToPlayer.putExtra("video list", vidList);
//                        intentToPlayer.putExtra("thumbnail url",thumbnailUrl );
//                        intentToPlayer.putExtra("videoID", videoId);
//                        startActivity(intentToPlayer);

                    }
                });
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                super.onPostExecute(result);
            } catch (Exception e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

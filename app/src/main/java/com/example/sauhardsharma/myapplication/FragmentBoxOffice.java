package com.example.sauhardsharma.myapplication;


import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.sauhardsharma.myapplication.VolleySingleton;
import static com.example.sauhardsharma.myapplication.Keys.EndpointBoxOffice.*;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link com.example.sauhardsharma.myapplication.FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toolbar toolbar;

    public static final String URL_BOX_OFFICE="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    public static final String URL_CHAR_QUESTION="?";
    public static final String URL_CHAR_AMPERSAND="&";
    public static final String URL_PARAM_API_KEY="apikey=";
    public static final String URL_PARAM_LIMIT="limit=";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<HollyMovie> listMovies=new ArrayList<>();
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
   private AdapterBoxOffice adapterBoxOffice;
    private RecyclerView listMovieHits;
    Context appContext;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2 ){
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getRequestUrl(int limit) {
        return  URL_BOX_OFFICE + URL_CHAR_QUESTION + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES+ URL_CHAR_AMPERSAND+
                URL_PARAM_LIMIT+limit;


    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();
    }





    private void sendJsonRequest() {
        Log.d("ass",getRequestUrl(10));
        JsonObjectRequest request=new JsonObjectRequest(getRequestUrl(10),null,new Response.Listener<JSONObject>() {



            @Override
            public void onResponse(JSONObject response) {
          listMovies=parseJSONResponse(response);
                adapterBoxOffice.setMovies(listMovies);

               //parseJSONResponse(response);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.response",error.toString());
            }
        });
        requestQueue.add(request);
    }
    private ArrayList<HollyMovie> parseJSONResponse(JSONObject response) {
        ArrayList<HollyMovie> listMovies = new ArrayList<>();
        if (response != null && response.length() != 0) {
             //String KEY_MOVIES="movies";
            //String KEY_ID="id";
            try {

                JSONArray arrayMovies = response.getJSONArray("movies");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    long id = currentMovie.getLong("id");
                    String title = currentMovie.getString("title");

                   System.out.println(title);

                    JSONObject objectReleaseDates = currentMovie.getJSONObject("release_dates");
                    String releaseDate = null;
                    if (objectReleaseDates.has("theater")) {
                        releaseDate = objectReleaseDates.getString("theater");
                    } else {
                        releaseDate = "NA";
                    }
                    JSONObject objectRatings = currentMovie.getJSONObject("ratings");
                    int audienceScore = -1;
                    if (objectRatings.has("audience_score")) {
                        audienceScore = objectRatings.getInt("audience_score");
                    }
                    String synopsis = currentMovie.getString("synopsis");

                    JSONObject objectPosters = currentMovie.getJSONObject("posters");
                    String urlThumbnail = null;
                    if (objectPosters.has("thumbnail")) {
                        urlThumbnail = objectPosters.getString("thumbnail");
                    }
                    HollyMovie movie = new HollyMovie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = dateFormat.parse(releaseDate);
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);
                    listMovies.add(movie);

                  // data.append(id + " " + title + " " + releaseDate + " " + audienceScore + "\n");
                }
             //  L.t(getActivity(), listMovies.toString());

            } catch (JSONException e) {
                System.out.print("sdjf");

            } catch (ParseException e) {

            }

        }
        return listMovies;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_fragment_box_office, container, false);
        View view=inflater.inflate(R.layout.fragment_box_office,container,false);
        listMovieHits =(RecyclerView)view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice=new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
sendJsonRequest();
        return view;
    }


    @Override
    public void onClick(View v) {

    }
}

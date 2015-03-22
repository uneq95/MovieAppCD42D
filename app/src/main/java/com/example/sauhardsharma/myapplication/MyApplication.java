package com.example.sauhardsharma.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by nairit on 8/3/15.
 */
public class MyApplication extends Application {
    public static final String API_KEY_ROTTEN_TOMATOES="7mvn6ccuuukd79n4yxk8y2qx";
    private static MyApplication sInstance;



      @Override

   public void onCreate(){
          super.onCreate();
          sInstance=this;

      }
    public static MyApplication getInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}

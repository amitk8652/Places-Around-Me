package com.example.maurya.searchmap;

/**
 * Created by maurya on 14/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread background = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MapsActivity.class);
                    startActivity(intent);
                }
            }
        };
        background.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}

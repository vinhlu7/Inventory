package com.inventory.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity{

	private static int SPLASH_TIME_OUT = 3000; //ms
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, InventoryActivity.class);
                startActivity(intent);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
	
}

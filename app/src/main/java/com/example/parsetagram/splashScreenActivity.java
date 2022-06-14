package com.example.parsetagram;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.ParseUser;

public class splashScreenActivity extends Activity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ParseUser.getCurrentUser() != null){
                    Intent intent=new Intent(splashScreenActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(splashScreenActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }

}
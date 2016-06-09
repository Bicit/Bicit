package com.bicit.vista;

/**
 * Created by piipe on 3/16/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bicit.bicit.R;

public class bicitSplash extends Activity {
    // ===========================================================
    // Fields
    // ===========================================================
    private final int SPLASH_DISPLAY_LENGHT = 1000;
    // ===========================================================
    // "Constructors"
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splashscreen);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(bicitSplash.this,LoginActivity.class);
                bicitSplash.this.startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
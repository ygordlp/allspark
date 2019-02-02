package com.transformers.allspark.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.transformers.allspark.R;

/**
 * Loading activity.
 * It is call to start the app and starting the loading process.
 */
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
}

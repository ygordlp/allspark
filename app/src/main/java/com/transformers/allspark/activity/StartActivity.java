package com.transformers.allspark.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.model.Transformers;

/**
 * Loading activity.
 * It is call to start the app and starting the loading process.
 */
public class StartActivity extends AppCompatActivity implements AllSparkApp.LoaderListener {

    /** Shows start screen for at least 2 seconds. */
    private static final long WAIT_TIME = 2000;
    private static final String TAG = "StartActivity";
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startTime = System.currentTimeMillis();

        AllSparkApp app = (AllSparkApp) getApplication();
        app.init(this);
    }

    @Override
    public void onLoadFinished(Transformers transformers) {
        Log.d(TAG, "On api load finished");
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;

        if(diff < WAIT_TIME){
            long remain = WAIT_TIME - diff;
            new CountDownTimer(remain, remain){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    goToMainActivity();
                }
            }.start();
        } else {
            goToMainActivity();
        }
    }

    private void goToMainActivity(){
        Log.d(TAG, "Starting main activity.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

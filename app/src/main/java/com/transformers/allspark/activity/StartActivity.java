package com.transformers.allspark.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;

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

    private void goToMainActivity(){
        Log.d(TAG, "Starting main activity.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadFinished() {
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

    @Override
    public void onLoadFail(String reason) {
        String failMessage = String.format(getString(R.string.str_server_fail), reason);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(failMessage)
                .setPositiveButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setCancelable(false);
        builder.create().show();
    }
}

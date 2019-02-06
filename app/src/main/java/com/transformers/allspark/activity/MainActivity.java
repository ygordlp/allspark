package com.transformers.allspark.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.transformers.allspark.R;
import com.transformers.allspark.fragment.BattleFragment;
import com.transformers.allspark.fragment.CreateFragment;
import com.transformers.allspark.fragment.TransformersFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_transformers:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new TransformersFragment())
                            .commitNow();
                    return true;
                case R.id.navigation_create:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new CreateFragment())
                            .commitNow();
                    return true;
                case R.id.navigation_battle:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new BattleFragment())
                            .commitNow();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TransformersFragment())
                .commitNow();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

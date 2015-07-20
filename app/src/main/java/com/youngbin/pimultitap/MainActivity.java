package com.youngbin.pimultitap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        Button Btn = (Button)findViewById(R.id.btn);
        SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean firstrun = SP.getBoolean("firstrun",true);
        if(firstrun){
            startActivity(new Intent(mContext, ConnectActivity.class));
            SP.edit().putBoolean("firstrun",false).commit();
        }
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ConnectActivity.class));
            }
        });
    }

}

package com.youngbin.pimultitap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Context mContext;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize UI and Variables
        mContext = MainActivity.this;
        NavigationView NV = (NavigationView)findViewById(R.id.navigationView);
        NV.setNavigationItemSelectedListener(this);
        SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String IP = SP.getString("ip","none");
        boolean firstrun = SP.getBoolean("firstrun",true);
        if(firstrun){
            startActivity(new Intent(mContext, ConnectActivity.class));
            SP.edit().putBoolean("firstrun",false).commit();
        }
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        //Load data then build list
        loadData(IP);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.reconnect:
                startActivity(new Intent(mContext, ConnectActivity.class));
        }
        return false;
    }

    void loadData(String IP){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://"+IP)
                .build();
        PiMultiTapServer.PiMultiTapREST rest = restAdapter.create(PiMultiTapServer.PiMultiTapREST.class);
        rest.getAllConfig(new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}

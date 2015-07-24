package com.youngbin.pimultitap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConnectActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        final Context mContext = ConnectActivity.this;
        Button ConnectBtn = (Button)findViewById(R.id.connectbtn);
        Button CancelBtn = (Button)findViewById(R.id.cancelbtn);
        final TextView StateTxt = (TextView)findViewById(R.id.state);
        final EditText IPInput = (EditText)findViewById(R.id.ipInput);
        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);

        ConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateTxt.setText(getResources().getString(R.string.connection_connecting));
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://"+IPInput.getText())
                        .build();

                PiMultiTapServer.PiMultiTapREST rest = restAdapter.create(PiMultiTapServer.PiMultiTapREST.class);
                rest.getInfo(new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        Log.d("JSON RES",jsonObject.toString());
                        Log.d("JSON RES",jsonObject.get("pimultitap").toString());
                        if(jsonObject.get("pimultitap").getAsString().equals("PiMultiTap")){
                            StateTxt.setText(getResources().getString(R.string.connection_connected));
                            SP.edit().putString("ip",IPInput.getText().toString()).apply();
                            SP.edit().putString("name",jsonObject.get("name").getAsString()).apply();
                            SP.edit().putString("desc",jsonObject.get("desc").getAsString()).apply();
                            finish();
                        }else{
                            StateTxt.setText(getResources().getString(R.string.connection_notpimultitap));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("ERR!",error.toString());
                        StateTxt.setText(getResources().getString(R.string.connection_failed));
                    }
                });
            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
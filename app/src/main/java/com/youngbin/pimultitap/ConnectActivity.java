package com.youngbin.pimultitap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ConnectActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        getSupportActionBar().hide();

        final Context mContext = ConnectActivity.this;
        final OkHttpClient client = new OkHttpClient();
        Button ConnectBtn = (Button)findViewById(R.id.connectbtn);
        Button CancelBtn = (Button)findViewById(R.id.cancelbtn);
        final TextView StateTxt = (TextView)findViewById(R.id.state);
        final EditText IPInput = (EditText)findViewById(R.id.ipInput);
        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);

        ConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final String IP = IPInput.getText().toString();
                Log.d("TAG","http://"+IP+"/getinfo");
                Request request = new Request.Builder()
                        .url("http://"+IP+"/getinfo")
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {
//                        StateTxt.setText(getResources().getString(R.string.connection_failed));

//                        Toast.makeText(mContext,getResources().getString(R.string.connection_failed),Toast.LENGTH_SHORT).show();
                        Log.d("TAG","http://"+IP+"/getinfo"+" - FAIL");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Log.d("TAG","http://"+IP+"/getinfo"+" - OK");
                        String BODY = response.body().toString();
                        JSONObject JsonObj;
                        try {
                            JsonObj = new JSONObject(BODY);
                            if(JsonObj.getString("pimultitap").equals("PiMultiTap")){
                                SP.edit().putString("pimultitap_name",JsonObj.getString("name")).apply();
                                SP.edit().putString("pimultitap_desc",JsonObj.getString("desc")).apply();
                                SP.edit().putString("pimultitap_ip",IP).apply();
//                                StateTxt.setText(getResources()
//                                        .getString(R.string.connection_connected)+JsonObj.getString("name"));
//                                Toast.makeText(mContext,
//                                        getResources().getString(R.string.connection_connected)+JsonObj.getString("name"),
//                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println(response.body().string());
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

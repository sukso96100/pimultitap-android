package com.youngbin.pimultitap;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by youngbin on 15. 7. 22.
 */
public class PiMultiTapServer {
    public interface PiMultiTapREST {
        @GET("/getinfo")
        public void getInfo(Callback<JsonObject> callback);

        @GET("/settings")
        public void getAllConfig(Callback<JsonObject> callback);

        @POST("/settings/save/{num}")
        public void saveConfig(@Path("num") int Num,@Body String s, Callback<String> Res);
    }
}

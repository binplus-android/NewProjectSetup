package com.example.newprojectsetup.Config;


import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiCall {


    @Multipart
    @POST("Verified_Wallet")
    Call<JsonObject> upload_profile_pic(@Part MultipartBody.Part p, @Part MultipartBody.Part adf, @Part MultipartBody.Part adb, @Part MultipartBody.Part pa, @PartMap HashMap<String, RequestBody> params);

}
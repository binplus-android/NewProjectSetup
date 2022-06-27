package com.example.newprojectsetup.Common;

import static android.accounts.AccountManager.KEY_PASSWORD;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.newprojectsetup.Activity.LoginActivity;
import java.util.HashMap;

public class Session_management {

    public static final String prefs = "AppPrefrence";
    private static final String DEVICE_ID ="" ;
    Context context;

    static SharedPreferences pref;
    public SharedPreferences.Editor editor;
    String data = "";

    public Session_management(Context context) {

        this.context = context;
        pref = context.getSharedPreferences(prefs, context.MODE_PRIVATE);
        editor = pref.edit();

    }

    public void saveISRegister(String name, String password, String email, String mobile, String alt_mobile, String address, String city, String village, String district, String state, String pincode, String age, String con_password, String gen, boolean isRegister) {
        editor.putBoolean("isLogin", true);
        editor.putString("name", name);
//        editor.putString("hint_name", hint_name);
//        editor.putString("lastname", lastname);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("alt_mobile", alt_mobile);

        editor.putString("address", address);
        editor.putString("age", age);
        editor.putString("password", password);
        editor.putString("con_password", con_password);


        // editor.putString("image",image);
        editor.putString("password", password);

        editor.commit();
    }

    public boolean saveIsLogin(Context context, String mobile, String password, boolean isLogin) {
        editor.putBoolean("isLogin", isLogin);
        editor.putString("mobile", mobile);
        editor.putString("password", password);
        editor.commit();
        return isLogin;
    }

    public static boolean getIsLogin() {

        return pref.getBoolean("isLogin", false);
    }

    public void saveIsProfile(Context context, String name, String email, String mobile, String address, String gen, String image, boolean isProfile) {

        editor.putBoolean("isLogin", isProfile);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("address", address);
        editor.putString("gen", gen);
        editor.putString("image", image);
        // editor.putString("imagePreferance",  BitMapToString( bm) );
        editor.commit();
    }

    public boolean getIsProfile(Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefs, context.MODE_PRIVATE);
        return pref.getBoolean("isLogin", false);
    }

    public void logout(Context context) {

        editor.clear();
        editor.commit();
        Intent logout = new Intent(context, LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(logout);
        Log.e("logout", "logout: " + logout);


    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id

        // return user
        return user;
    }

    public void createLoginSession(String id, String email, String name
            , String mobile, String image, String wallet_ammount, String reward_point, String pincode, String city_id,
                                   String password) {


        editor.putString(KEY_PASSWORD, password);


        editor.commit();
    }

    public void setAddress(String id) {


        editor.commit();
    }

    public HashMap<String, String> getAddressDetails() {
        HashMap<String, String> user = new HashMap<String, String>();


        return user;
    }

    public void setCategoryId(String id) {

        editor.commit();
    }


    public void addDeviceId(String deviceId) {
        editor.putString(DEVICE_ID,deviceId);
        editor.commit();
    }

    public String getDeviceId() {
        return pref.getString(DEVICE_ID,"");
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();

    }
}
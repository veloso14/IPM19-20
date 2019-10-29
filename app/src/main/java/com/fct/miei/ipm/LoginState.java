package com.fct.miei.ipm;

import android.content.Context;

public class LoginState {
    SharedPrefsUtil sharedPrefsUtil;

    public LoginState(Context ctx){
        sharedPrefsUtil = SharedPrefsUtil.getInstance(ctx);
        System.out.println(sharedPrefsUtil);
    }

    public boolean isLoggedIn(){
        return sharedPrefsUtil.getBoolean(SharedPrefsUtil.Key.IS_LOGGED,false);
    }

    public void login(){
        sharedPrefsUtil.edit();
        sharedPrefsUtil.put(SharedPrefsUtil.Key.IS_LOGGED, true);
        sharedPrefsUtil.commit();
    }
    public void logout(){
        sharedPrefsUtil.edit();
        sharedPrefsUtil.put(SharedPrefsUtil.Key.IS_LOGGED, false);
        sharedPrefsUtil.commit();
    }
}

package com.hdu.innovationplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.innovationplatform.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * com.hdu.innovationplatform.utils
 * Created by 73958 on 2017/5/24.
 */

public class UserStatus {

    public static boolean LOGIN_STATUS = false;

    public static User USER;

    public static SharedPreferences SP;

    public static SharedPreferences.Editor EDITOR;

    public static void ClearUserLoginStatus(Context context){
        LOGIN_STATUS = false;
        USER = null;
        SP = context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
        SP.edit().clear().apply();
        LogUtil.e("unlogin success");
    }
}

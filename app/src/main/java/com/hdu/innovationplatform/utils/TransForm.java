package com.hdu.innovationplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.hdu.innovationplatform.utils.UserStatus.USER;

/**
 * com.hdu.innovationplatform.utils
 * Created by 73958 on 2017/5/24.
 */
public class TransForm {

    public static void syncUserInfo(String res, Context context){
        if (res != null){
            try {
                JSONObject json = new JSONObject(res);
                JSONObject info = json.getJSONObject("info");
                String real_name = info.getString("realname");
                String sex = info.getString("sex");
                String telephone = info.getString("telephone");
                String driver_licence_number = info.getString("driver_licence_number");
                String car_type = info.getString("car_type");
                String car_number = info.getString("car_number");

                USER.setName(real_name);
//                USER.setDriverNumber(driver_licence_number);
//                USER.setDriverType(car_type);
//                USER.setCarNumber(car_number);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                sp.edit().putString("user_real_name", real_name).apply();
                sp.edit().putString("user_phone_number", USER.getUsername()).apply();
                sp.edit().putString("user_driver_number", driver_licence_number).apply();
                sp.edit().putString("user_driver_type", car_type).apply();
                sp.edit().putString("user_car_number", car_number).apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用当前系统日期
     */
    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    /**
     * 用当前系统时间
     */
    public static String getTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        return dateFormat.format(date);
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}

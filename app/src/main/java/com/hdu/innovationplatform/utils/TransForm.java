package com.hdu.innovationplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hdu.innovationplatform.model.Blog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static ArrayList<Blog> parseBlog(String res){
        if (res != null){
            ArrayList<Blog> blogs = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(res);
                for(int i = 0; i < array.length(); i++){
                    JSONObject json = array.getJSONObject(i);
                    String author = json.getString("Author");
                    String label = json.getString("Label");
                    String title = json.getString("Title");
                    String content = json.getString("Content");

                    Blog blog = new Blog(title, label, author, content);
                    blogs.add(blog);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return blogs;
        }
        return null;
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
}

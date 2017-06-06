package com.hdu.innovationplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
                String userId = json.getString("user_id");
                String real_name = json.getString("name");
                String sex = json.getString("sex");
                int school_num = json.getInt("school_num");

                USER.setUserId(userId);
                USER.setName(real_name);
                USER.setSex(sex);
                USER.setSchool_num(school_num);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                sp.edit().putString("user_real_name", real_name).apply();
                sp.edit().putString("user_sex", USER.getSex()).apply();
                sp.edit().putString("user_school_number", String.valueOf(USER.getSchool_num())).apply();
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
                    String id = json.getString("Id");
                    String author_id = json.getString("Author_Id");
                    String author = json.getString("Author");
                    String label = json.getString("Label");
                    String title = json.getString("Title");
                    String content = json.getString("Content");

                    Blog blog = new Blog(id, author_id, title, label, author, content);
                    blogs.add(blog);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return blogs;
        }
        return null;
    }

    public static ArrayList<Comment> parseComment(String res){
        if (res != null){
            ArrayList<Comment> comments = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(res);
                for(int i = 0; i < array.length(); i++){
                    JSONObject json = array.getJSONObject(i);
                    String id = json.getString("User_Id");
                    String username = json.getString("Username");
                    String name = json.getString("Name");
                    String article_id = json.getString("Article_Id");
                    String content = json.getString("Content");
                    String time = json.getString("updatedAt");

                    Comment comment = new Comment(id, username, name, article_id, content, time);
                    comments.add(comment);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return comments;
        }
        return null;
    }

    public static ArrayList<Blog> parseFollowedBlog(String res){
        if (res != null){
            ArrayList<Blog> blogs = new ArrayList<>();
            ArrayList<String> authorId = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(res);
                for(int i = 0; i < array.length(); i++){
                    JSONObject json = array.getJSONObject(i);
                    String id = json.getString("Id");
                    String author_id = json.getString("Author_Id");
                    String author = json.getString("Author");
                    String label = json.getString("Label");
                    String title = json.getString("Title");
                    String content = json.getString("Content");

                    authorId.add(author_id);
                    Blog blog = new Blog(id, author_id, title, label, author, content);
                    blogs.add(blog);
                }
                USER.setFollowed_id(authorId);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

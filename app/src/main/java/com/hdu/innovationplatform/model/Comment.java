package com.hdu.innovationplatform.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * com.hdu.innovationplatform.model
 * Created by 73958 on 2017/6/4.
 */

public class Comment implements Parcelable {
    private String userId;
    private String username;
    private String name;
    private String articleId;
    private String content;
    private String time;

    public Comment(String userId, String username, String name, String articleId, String content) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.articleId = articleId;
        this.content = content;
    }

    public Comment(String userId, String username, String name, String articleId, String content, String time) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.articleId = articleId;
        this.content = content;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.articleId);
        dest.writeString(this.content);
        dest.writeString(this.time);
    }

    protected Comment(Parcel in) {
        this.userId = in.readString();
        this.username = in.readString();
        this.name = in.readString();
        this.articleId = in.readString();
        this.content = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

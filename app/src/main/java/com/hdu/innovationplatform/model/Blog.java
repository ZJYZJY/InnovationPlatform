package com.hdu.innovationplatform.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * com.hdu.innovationplatform.model
 * Created by 73958 on 2017/5/25.
 */

public class Blog implements Parcelable {

    private String articleId;
    private String user_id;
    private String title;
    private String label;
    private String author;
    private String content;
    private ArrayList<Comment> comments;

    public Blog(String user_id, String title, String label, String author, String content) {
        this.user_id = user_id;
        this.title = title;
        this.label = label;
        this.author = author;
        this.content = content;
    }

    public Blog(String articleId, String user_id, String title, String label, String author, String content) {
        this.articleId = articleId;
        this.user_id = user_id;
        this.title = title;
        this.label = label;
        this.author = author;
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.articleId);
        dest.writeString(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.label);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeList(this.comments);
    }

    protected Blog(Parcel in) {
        this.articleId = in.readString();
        this.user_id = in.readString();
        this.title = in.readString();
        this.label = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel source) {
            return new Blog(source);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };
}

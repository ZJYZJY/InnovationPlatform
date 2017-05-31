package com.hdu.innovationplatform.model;

/**
 * com.hdu.innovationplatform.model
 * Created by 73958 on 2017/5/25.
 */

public class Blog {

    private String userId;
    private String title;
    private String label;
    private String author;
    private String content;

    public Blog(String title, String label, String author, String content) {
        this.title = title;
        this.label = label;
        this.author = author;
        this.content = content;
    }

    public Blog(String userId, String title, String label, String author, String content) {
        this.userId = userId;
        this.title = title;
        this.label = label;
        this.author = author;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

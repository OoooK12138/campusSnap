package com.example.campussnap.config;

import lombok.Data;

// 新闻列表item的数据结构

@Data
public class ListItem {
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String title;
    private String content;
    private String date;
    private Integer imgUrl;

    public ListItem(String title, String content, String date, Integer imgUrl) {
        setTitle(title);
        setContent(content);
        setDate(date);
        setImgUrl(imgUrl);
    }

}

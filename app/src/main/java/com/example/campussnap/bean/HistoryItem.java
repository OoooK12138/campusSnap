package com.example.campussnap.bean;


public class HistoryItem {
    private String title;
    private String content;
    private String date;
    private String category;
    private String process;
    private Integer id;
    private Integer imgUrl;



    public HistoryItem(String title,String content,String date,Integer imgUrl,Integer id,String category,String process){
        this.category=category;
        this.content=content;
        this.date=date;
        this.imgUrl=imgUrl;
        this.title=title;
        this.id=id;
        this.process=process;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDate(){
        return this.date;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getProcess() {
        return process;
    }

    public String getCategory() {
        return category;
    }
}

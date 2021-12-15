package com.example.campussnap.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBean {

    private Integer id;
    private String imageUrl;
    private String title;
    private String desc;
    private String publishAccount;
    private Date publishTime;

}

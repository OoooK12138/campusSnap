package com.example.campussnap.bean;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItem {
    private Integer id;
    private String process;
    private String address;
    private Integer degree;
    private Date time;
    private String title;
    private String category;
    private String account;
    private String desc;
    private String imageUrl;

}

package com.example.campussnap.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackBean {
    private Integer Id;
    private Integer feedBackId;
    private String desc;
    private Date time;
    private String imageUrl;
}

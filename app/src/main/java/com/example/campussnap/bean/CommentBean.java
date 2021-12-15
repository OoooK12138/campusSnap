package com.example.campussnap.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBean {
    private long feedBackId;
    private int ratingResult;
    private int ratingSpeed;
    private String commend;

}

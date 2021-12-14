package com.example.campussnap.common;

import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    private String key;
    private String value;

    public String getParam(){
        return key + "=" + URLEncoder.encode(value);
    }
}

package com.example.campussnap.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code; //响应码
    private String message;
    private Object data; //返回数据

    public boolean isSuccess(){
        if (message.equals("SUCCESS")){
            return true;
        }
        return false;
    }


}

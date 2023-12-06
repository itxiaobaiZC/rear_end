package com.itzc.rear_end.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//接口同意返回包装类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }
    public static Result error(String data,String msg){
        return new Result(data,msg,data);
    }
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误！",null);
    }
}

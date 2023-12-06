package com.itzc.rear_end.controller.dto;

import com.itzc.rear_end.entity.SysMenu;
import lombok.Data;

import java.util.List;

//接受前端登录请求
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
    private String uname;
    private String avatarUrl;
    private String number;
    private String address;
    private String email;
    private String token;
    private String role;
    private List<SysMenu> menus;

}

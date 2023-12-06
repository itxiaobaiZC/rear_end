package com.itzc.rear_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author itzc
 * @since 2023-10-27
 */
@Getter
@Setter
@TableName("sys_user")
//@ApiModel(value = "SysUser对象", description = "")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String email;

    private String uname;

//    @ApiModelProperty("电话")
    private String number;

//    @ApiModelProperty("地址")
    private String address;
    private String avatarUrl;
    private Date createTime;
    private String role;


}

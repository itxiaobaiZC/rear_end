package com.itzc.rear_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author itzc
 * @since 2023-11-22
 */
@Getter
@Setter
  @TableName("t_policycomment")

public class Policycomment implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String content;

    private Integer userId;

    private String time;

    private Integer pid;

    private Integer originId;

    private Integer articleId;

    @TableField(exist = false)
    private String pNickname;  // 父节点的用户昵称
    @TableField(exist = false)
    private Integer pUserId;  // 父节点的用户id

    @TableField(exist = false)
    private String uname;
    @TableField(exist = false)
    private String avatarUrl;
    @TableField(exist = false)
    private List<Policycomment> children;


}

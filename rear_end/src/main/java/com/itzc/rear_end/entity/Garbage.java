package com.itzc.rear_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author itzc
 * @since 2023-11-09
 */
@Getter
@Setter
public class Garbage implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;
    private String picurl;
    private String name;

    private String category;

    private String price;

    private String descrition;


}

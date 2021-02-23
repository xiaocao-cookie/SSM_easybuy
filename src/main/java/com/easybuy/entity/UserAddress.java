package com.easybuy.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {
    private Integer id;
    private String address;
    private Integer userId;
    private Date createTime;//创建时间
    private Integer isDefault;
    private String remark;
}

package randomnick.eleco.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -5051120337175047163L;

    //用户ID,雪花算法，自增ID
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    //用户名
    @TableField("username")
    private String username;

    //用户昵称
    @TableField("alias")
    private String alias;

    //密码
    @JsonIgnore()
    @TableField("password")
    private String password;

    //头像
    @Builder.Default
    @TableField("avatar")
    private String avatar = "https://s3.ax1x.com/2020/12/01/DfHNo4.jpg";

    //邮箱
    @TableField("email")
    private String email;

    //电话
    @TableField("mobile")
    private String mobile;

    //个人简介
    @Builder.Default
    @TableField("bio")
    private String bio = "这个人好懒，什么都没有简介";

    //积分
    @Builder.Default
    @TableField("score")
    private Integer score = 0;

    @JsonIgnore
    @TableField("token")
    private String token;

    //是否激活，1：是，0：否
    @Builder.Default
    @TableField("active")
    private Boolean active = true;

    /**
     * 状态。1:使用，0:已停用
     */
    @Builder.Default
    @TableField("`status`")
    private Boolean status = true;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}

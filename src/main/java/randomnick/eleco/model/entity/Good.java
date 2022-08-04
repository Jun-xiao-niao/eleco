package randomnick.eleco.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {  //商品信息
    private String id;
    private String goodDescrip; //商品简介（商品描述）
    private double price;
    private String type; //商品标签 用于推荐和展示
    private String sellerId; //卖家ID
    private String buyerId; //卖家ID
    private String state; //交易状态
    private String pictureUrl;  //图片URL

    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private LocalDateTime createTime;  //上架时间
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private LocalDateTime updateTime;  //交易时间
}

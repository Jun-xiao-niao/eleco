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
public class SellGood { //返回商品信息和卖家部分信息
    private String id;
    private String goodDescrip;
    private double price;
    private String type; //商品标签 用于推荐和展示
    private String sellerId; //卖家ID
    private String sellerName; //卖家名
    private String sellerPicUrl; //卖家头像
    private String buyerId; //买家ID
    private String state; //交易状态
    private String goodPictureUrl;  //图片URL

    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private LocalDateTime createTime;  //上架时间
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private LocalDateTime updateTime;  //交易时间
}

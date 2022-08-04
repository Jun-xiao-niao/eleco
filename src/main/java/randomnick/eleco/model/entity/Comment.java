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
public class Comment {  //商品评论
    private String id;
    private String userId;
    private String goodId;
    private String content;

    @TableField(fill = FieldFill.INSERT ,value = "create_time")
    private LocalDateTime createTime;  //评论时间
}

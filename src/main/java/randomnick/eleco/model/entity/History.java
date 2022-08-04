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
public class History {  //历史浏览
    private String id;
    private String userId;
    private String goodId;

    @TableField(fill = FieldFill.INSERT ,value = "create_time")
    private LocalDateTime createTime;  //点击时间
}

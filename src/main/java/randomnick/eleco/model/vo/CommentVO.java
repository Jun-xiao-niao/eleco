package randomnick.eleco.model.vo;


import lombok.Data;

import java.util.Date;

//返回给客户端的评论信息,增加评论者用户名
@Data
public class CommentVO {

    private String id;

    private String content;

    private String topicId;

    private String userId;

    private String username;

    private Date createTime;

}

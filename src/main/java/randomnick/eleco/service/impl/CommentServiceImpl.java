package randomnick.eleco.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import randomnick.eleco.mapper.CommentMapper;
import randomnick.eleco.model.dto.CommentDTO;
import randomnick.eleco.model.entity.Comment;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.CommentVO;
import randomnick.eleco.service.CommentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<CommentVO> getCommentsByTopicID(String topicid) {
        List<CommentVO> comment = new ArrayList<>();
        try {
            comment = this.baseMapper.getCommentsByTopicID(topicid);
        } catch (Exception e) {
            log.info("getCommentsByTopicID(topicid)失败");
        }
        return comment;
    }


    @Override
    public Comment create(CommentDTO dto, User user) {
        Comment comment = Comment.builder()
                .userId(user.getId())
                .content(dto.getContent())
                .topicId(dto.getTopic_id())
                .createTime(new Date())
                .build();
        this.baseMapper.insert(comment);
        return comment;
    }
}

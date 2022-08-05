package randomnick.eleco.service;

import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.dto.CommentDTO;
import randomnick.eleco.model.entity.Comment;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.CommentVO;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**
     *
     *
     * @param topicid
     * @return {@link Comment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    Comment create(CommentDTO dto, User principal);
}

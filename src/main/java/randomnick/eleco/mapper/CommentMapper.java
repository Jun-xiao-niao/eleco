package randomnick.eleco.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import randomnick.eleco.model.entity.Comment;
import randomnick.eleco.model.vo.CommentVO;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * getCommentsByTopicID
     *
     * @param topicid
     * @return
     */
    List<CommentVO> getCommentsByTopicID(@Param("topicid") String topicid);
}

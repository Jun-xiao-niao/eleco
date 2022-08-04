package randomnick.eleco.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.entity.TopicTag;

import java.util.List;

public interface TopicTagService extends IService<TopicTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<TopicTag> selectByTopicId(String topicId);

}

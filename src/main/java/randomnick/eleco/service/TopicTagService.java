package randomnick.eleco.service;

import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.entity.Tag;
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
    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<Tag> tags);

}

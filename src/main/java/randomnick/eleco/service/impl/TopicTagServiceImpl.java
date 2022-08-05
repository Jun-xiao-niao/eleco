package randomnick.eleco.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import randomnick.eleco.model.entity.Tag;
import randomnick.eleco.service.TopicTagService;
import randomnick.eleco.mapper.TopicTagMapper;
import randomnick.eleco.model.entity.TopicTag;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicTagServiceImpl extends ServiceImpl<TopicTagMapper, TopicTag> implements TopicTagService {

    @Override
    public List<TopicTag> selectByTopicId(String topicId) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public void createTopicTag(String id, List<Tag> tags) {
        // 先删除topicId对应的所有记录
        this.baseMapper.delete(new LambdaQueryWrapper<TopicTag>().eq(TopicTag::getTopicId, id));

        // 循环保存对应关联
        tags.forEach(tag -> {
            TopicTag topicTag = new TopicTag();
            topicTag.setTopicId(id);
            topicTag.setTagId(tag.getId());
            this.baseMapper.insert(topicTag);
        });
    }

}

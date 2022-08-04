package randomnick.eleco.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import randomnick.eleco.Service.TopicTagService;
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

}

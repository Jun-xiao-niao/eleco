package randomnick.eleco.Service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import randomnick.eleco.Service.PostService;
import randomnick.eleco.Service.TopicTagService;
import randomnick.eleco.mapper.TagMapper;
import randomnick.eleco.mapper.TopicMapper;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.Tag;
import randomnick.eleco.model.entity.TopicTag;
import randomnick.eleco.model.vo.PostVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {



    @Resource
    private TagMapper bmsTagMapper;

    @Autowired
    private randomnick.eleco.Service.TopicTagService TopicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        iPage.getRecords().forEach(topic -> {
            List<TopicTag> topicTags = TopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = TagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
        return iPage;
    }
}
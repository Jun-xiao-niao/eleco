package randomnick.eleco.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import randomnick.eleco.mapper.TagMapper;
import randomnick.eleco.model.entity.Tag;
import randomnick.eleco.service.PostService;
import randomnick.eleco.service.TagService;
import randomnick.eleco.service.TopicTagService;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag 实现类
 *
 * @author Knox 2020/11/7
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TopicTagService topicTagService;

    @Autowired
    private PostService postService;


    @Override
    public List<Tag> insertTags(List<String> tagNames) {
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getName, tagName));
            if (tag == null) {
                tag = Tag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            } else {
                tag.setTopicCount(tag.getTopicCount() + 1);
                this.baseMapper.updateById(tag);
            }
            tagList.add(tag);
        }
        return tagList;
    }



}

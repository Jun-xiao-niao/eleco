package randomnick.eleco.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import randomnick.eleco.mapper.UserMapper;
import randomnick.eleco.model.dto.CreateTopicDTO;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.service.PostService;
import randomnick.eleco.mapper.TagMapper;
import randomnick.eleco.mapper.TopicMapper;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.Tag;
import randomnick.eleco.model.entity.TopicTag;
import randomnick.eleco.model.vo.PostVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {



    @Resource
    private TagMapper TagMapper;
    @Resource
    private UserMapper umsUserMapper;

    @Autowired
    @Lazy
    private randomnick.eleco.service.TagService TagService;

    @Autowired
    private randomnick.eleco.service.TopicTagService TopicTagService;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Post create(CreateTopicDTO dto, User user) {
        Post topic1 = this.baseMapper.selectOne(new LambdaQueryWrapper<Post>().eq(Post::getTitle, dto.getTitle()));
        Assert.isNull(topic1, "话题已存在，请修改");

        // 封装
        Post topic = Post.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);

        // 用户积分增加
        int newScore = user.getScore() + 1;
        umsUserMapper.updateById(user.setScore(newScore));

        // 标签
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // 保存标签
            List<Tag> tags = TagService.insertTags(dto.getTags());
            // 处理标签与话题的关联
            TopicTagService.createTopicTag(topic.getId(), tags);
        }

        return topic;
    }
}
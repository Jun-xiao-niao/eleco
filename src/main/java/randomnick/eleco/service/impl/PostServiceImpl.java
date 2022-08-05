package randomnick.eleco.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import randomnick.eleco.model.vo.ProfileVO;
import randomnick.eleco.service.PostService;
import randomnick.eleco.mapper.TagMapper;
import randomnick.eleco.mapper.TopicMapper;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.Tag;
import randomnick.eleco.model.entity.TopicTag;
import randomnick.eleco.model.vo.PostVO;
import randomnick.eleco.service.TagService;
import randomnick.eleco.service.TopicTagService;
import randomnick.eleco.service.UserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {



    @Resource
    private TagMapper tagMapper;
    @Resource
    private UserMapper userMapper;

    @Autowired
    @Lazy
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicTagService topicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        iPage.getRecords().forEach(topic -> {
            List<TopicTag> topicTags = topicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
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
        userMapper.updateById(user.setScore(newScore));

        // 标签
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // 保存标签
            List<Tag> tags = tagService.insertTags(dto.getTags());
            // 处理标签与话题的关联
            topicTagService.createTopicTag(topic.getId(), tags);
        }

        return topic;
    }


    @Override
    public Map<String, Object> viewTopic(String id) {
        Map<String, Object> map = new HashMap<>(16);
        Post topic = this.baseMapper.selectById(id);
        Assert.notNull(topic, "当前话题不存在,或已被作者删除");
        // 查询话题详情
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        // emoji转码
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);
        // 标签
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();
        for (TopicTag articleTag : TopicTagService.list(wrapper)) {
            set.add(articleTag.getTagId());
        }
        List<Tag> tags = tagService.listByIds(set);
        map.put("tags", tags);

        // 作者

        ProfileVO user = userService.getUserProfile(topic.getUserId());
        map.put("user", user);

        return map;
    }
}
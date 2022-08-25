package randomnick.eleco.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.model.dto.CreateTopicDTO;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.service.PostService;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.vo.PostVO;
import randomnick.eleco.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RestController
@Api("帖子功能")
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService postService;
    @Resource
    private UserService userService;

    @ApiOperation("获取所有帖子（tab == hot  代表最经7天最热帖子）（tab == latest  代表最新的帖子）")
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = postService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    @ApiOperation("发表帖子")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<Post> create(@RequestParam String username
            , @RequestBody CreateTopicDTO dto) {
        User user = userService.getUserByUsername(username);
        Post topic = postService.create(dto, user);
        return ApiResult.success(topic);
    }

    @ApiOperation("获取帖子")
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = postService.viewTopic(id);
        return ApiResult.success(map);
    }

    @ApiOperation("获取推荐的帖子")
    @GetMapping("/recommend")
    public ApiResult<List<Post>> getRecommend(@RequestParam("topicId") String id) {
        List<Post> topics = postService.getRecommend(id);
        return ApiResult.success(topics);
    }

    @ApiOperation("更新帖子")
    @PostMapping("/update")
    public ApiResult<Post> update(@RequestParam String username, @Valid @RequestBody Post post) {
        User user = userService.getUserByUsername(username);
        Assert.isTrue(user.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        postService.updateById(post);
        return ApiResult.success(post);
    }

    @ApiOperation("删除帖子")
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestParam String username, @PathVariable("id") String topicId) {
        User umsUser = userService.getUserByUsername(username);
        Post topic = postService.getById(topicId);
        Assert.notNull(topic, "话题不存在");
        Assert.isTrue(topic.getUserId().equals(umsUser.getId()), "不可以删除别人的话题哟！");
        postService.removeById(topicId);
        return ApiResult.success(null,"删除成功");
    }


}

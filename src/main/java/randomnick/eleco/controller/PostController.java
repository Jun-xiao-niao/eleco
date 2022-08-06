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

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static randomnick.eleco.jwt.JwtUtil.USER_NAME;

@Controller
@RestController
@Api("帖子功能")
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService PostService;
    @Resource
    private randomnick.eleco.service.UserService UserService;

    @ApiOperation("获取帖子")
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = PostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    @ApiOperation("发表帖子")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<Post> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        User user = UserService.getUserByUsername(userName);
        Post topic = PostService.create(dto, user);
        return ApiResult.success(topic);
    }

    @ApiOperation("获取帖子")
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = PostService.viewTopic(id);
        return ApiResult.success(map);
    }

    @ApiOperation("获取推荐的帖子")
    @GetMapping("/recommend")
    public ApiResult<List<Post>> getRecommend(@RequestParam("topicId") String id) {
        return ApiResult.success();
    }

    @ApiOperation("更新帖子")
    @PostMapping("/update")
    public ApiResult<Post> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody Post post) {
        return ApiResult.success(post);
    }

    @ApiOperation("删除帖子")
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {

        return ApiResult.success(null,"删除成功");
    }


}

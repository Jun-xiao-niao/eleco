package randomnick.eleco.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.model.dto.CreateTopicDTO;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.service.PostService;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.vo.PostVO;
import randomnick.eleco.service.UserService;

import javax.annotation.Resource;

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

}

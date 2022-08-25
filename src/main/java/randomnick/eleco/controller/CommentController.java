package randomnick.eleco.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.dto.CommentDTO;
import randomnick.eleco.model.entity.Comment;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.CommentVO;
import randomnick.eleco.service.CommentService;
import randomnick.eleco.service.UserService;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    @ApiOperation("获取评论，时间降序")
    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid") String topicid) {
        List<CommentVO> comment = commentService.getCommentsByTopicID(topicid);
        return ApiResult.success(comment);
    }

    @ApiOperation("添加评论")
    @PostMapping("/add_comment")
    public ApiResult<Comment> add_comment(@RequestParam String username,
                                          @RequestBody CommentDTO dto) {
        User user = userService.getUserByUsername(username);
        Comment comment = commentService.create(dto, user);
        return ApiResult.success(comment);
    }
}

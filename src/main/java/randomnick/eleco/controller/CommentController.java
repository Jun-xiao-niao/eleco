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

import static randomnick.eleco.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    @ApiOperation("获取评论")
    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = commentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }

    @ApiOperation("添加评论")
    @PostMapping("/add_comment")
    public ApiResult<Comment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                          @RequestBody CommentDTO dto) {
        User user = userService.getUserByUsername(userName);
        Comment comment = commentService.create(dto, user);
        return ApiResult.success(comment);
    }
}

//package randomnick.eleco.Controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import randomnick.eleco.mapper.CommentMapper;
//import randomnick.eleco.component.ResponseResult;
//import randomnick.eleco.model.entity.Comment;
//
//import java.util.List;
//
//import static randomnick.eleco.component.KeyUtil.getUniqueKey;
//
//@Controller
//@Api("用户操作评论")
//@RequestMapping("/comment")
//public class CommentController {
//
//    @Autowired
//    CommentMapper commentMapper;
//
//    @ApiOperation("查看所有商品所有评论")
//    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findAllUser() {
//        List<Comment> list = commentMapper.selectList(null);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("查找商品的评论")
//    @RequestMapping(value = "/findComment", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findComment(@RequestParam("goodId") String goodId) {
//        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
//        wrapper.eq("good_id", goodId);
//        List<Comment> list = commentMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("发布评论")
//    @RequestMapping(value = "/postComment", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseResult postComment(@RequestParam("goodId") String goodId,
//                                      @RequestParam("userId") String userId,
//                                      @RequestParam("content") String content) {
//        Comment comment = new Comment();
//        comment.setId(getUniqueKey());
//        comment.setGoodId(goodId);
//        comment.setUserId(userId);
//        comment.setContent(content);
//        commentMapper.insert(comment);
//        return new ResponseResult(200, "评论成功");
//    }
//
//    @ApiOperation("删除评论")
//    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseResult deleteComment(@RequestParam("id") String id) {
//        commentMapper.deleteById(id);
//        return new ResponseResult(200, "删除成功");
//    }
//
//}

package randomnick.eleco.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.common.api.ApiResult;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static randomnick.eleco.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseController {

    @ApiOperation("关注")
    @GetMapping("/subscribe/{userId}")
    public ApiResult<Object> handleFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {

        return ApiResult.success(null, "关注成功");
    }

    @ApiOperation("取消关注")
    @GetMapping("/unsubscribe/{userId}")
    public ApiResult<Object> handleUnFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {

        return ApiResult.success(null, "取关成功");
    }

    @ApiOperation("用户的关注")
    @GetMapping("/validate/{topicUserId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("topicUserId") String topicUserId) {
        return ApiResult.success(null, "取关成功");
    }
}

package randomnick.eleco.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.common.exception.ApiAsserts;
import randomnick.eleco.model.entity.Follow;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.service.FollowService;
import randomnick.eleco.service.UserService;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseController {

    @Resource
    private FollowService followService;

    @Resource
    private UserService userService;

    @ApiOperation("关注")
    @GetMapping("/subscribe/{parentId}")
    public ApiResult<Object> handleFollow(@RequestParam String username
            , @PathVariable("parentId") String parentId) {
        User user = userService.getUserByUsername(username);
        if (parentId.equals(user.getId())) {  //如果自己想关注自己
            ApiAsserts.fail("你不可以关注自己！");
        }
        //查看先前是否已关注
        Follow one = followService.getOne(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getParentId, parentId)
                        .eq(Follow::getFollowerId, user.getId()));
        if (!ObjectUtils.isEmpty(one)) {  //如果先前已关注
            ApiAsserts.fail("已关注");
        }

        Follow follow = new Follow();
        follow.setParentId(parentId);
        follow.setFollowerId(user.getId());
        followService.save(follow);
        return ApiResult.success(null, "关注成功");
    }

    @ApiOperation("取消关注")
    @GetMapping("/unsubscribe/{userId}")
    public ApiResult<Object> handleUnFollow(@RequestParam String username
            , @PathVariable("userId") String parentId) {
        User user = userService.getUserByUsername(username);
        //查看先前是否已关注
        Follow one = followService.getOne(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getParentId, parentId)
                        .eq(Follow::getFollowerId, user.getId()));
        if (ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("未关注！");
        }
        followService.remove(new LambdaQueryWrapper<Follow>().eq(Follow::getParentId, parentId)
                .eq(Follow::getFollowerId, user.getId()));
        return ApiResult.success(null, "取关成功");
    }

    @ApiOperation("查看两者是否为关注关系")
    @GetMapping("/validate/{topicUserId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestParam String username
            , @PathVariable("topicUserId") String topicUserId) {
        User user = userService.getUserByUsername(username);
        Map<String, Object> map = new HashMap<>(16);
        map.put("hasFollow", false);
        //查看先前是否已关注
        if (!ObjectUtils.isEmpty(user)) {
            Follow one = followService.getOne(new LambdaQueryWrapper<Follow>()
                    .eq(Follow::getParentId, topicUserId)
                    .eq(Follow::getFollowerId, user.getId()));
            if (!ObjectUtils.isEmpty(one)) { //已关注
                map.put("hasFollow", true);
            }
        }
        return ApiResult.success(map);
    }
}

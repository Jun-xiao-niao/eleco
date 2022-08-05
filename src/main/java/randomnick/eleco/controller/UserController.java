package randomnick.eleco.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.service.LoginService;
import randomnick.eleco.service.PostService;
import randomnick.eleco.service.UserService;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.component.ResponseResult;
import randomnick.eleco.model.dto.RegisterDTO;
import randomnick.eleco.model.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@Api("用户功能")
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @Autowired
    private LoginService loginService;
    @Resource
    private PostService postService;


    @ApiOperation("注册方法")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        User user = userService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }

    @ApiOperation("登录方法")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestParam("username") String name,
                                @RequestParam("password") String pwd,
                                HttpServletResponse response,
                                HttpSession session) {

        return loginService.login(name, pwd);

    }

    @ApiOperation("退出方法")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult logout() {
        return loginService.logout();

    }

    @ApiOperation("个人中心的信息")
    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByName(@PathVariable("username") String username,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        User user = userService.getUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        Page<Post> page = postService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<Post>().eq(Post::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }

    @ApiOperation("个人中心的信息的更新")
    @PostMapping("/update")
    public ApiResult<User> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return ApiResult.success(user);
    }
}

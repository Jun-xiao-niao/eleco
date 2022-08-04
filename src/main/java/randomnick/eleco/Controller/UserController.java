package randomnick.eleco.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import randomnick.eleco.Service.LoginService;
import randomnick.eleco.Service.UserService;
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
    private UserService UserService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("注册方法")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        User user = UserService.executeRegister(dto);
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
}

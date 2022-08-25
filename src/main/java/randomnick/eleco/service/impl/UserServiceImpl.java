package randomnick.eleco.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.common.exception.ApiAsserts;
import randomnick.eleco.jwt.JwtUtil;
import randomnick.eleco.mapper.UserMapper;
import randomnick.eleco.model.dto.LoginDTO;
import randomnick.eleco.model.dto.RegisterDTO;
import randomnick.eleco.model.entity.LoginUser;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.ProfileVO;
import randomnick.eleco.service.UserService;
import randomnick.eleco.utils.RedisCache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public User executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getName()).or().eq("email", dto.getEmail());
        User user = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(user)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User addUser = User.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(passwordEncoder.encode(dto.getPass()))//密码BCryptPasswordEncoder加密
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);

        return addUser;
    }

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public ApiResult<Map<String, String>> executeLogin(LoginDTO dto) {
        //authenticationManager.authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证未通过，给出相应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //如果认证通过了，使用username生成一个kwt jwt存入ApiResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String username = loginUser.getUsername();
        String jwt= JwtUtil.createJWT(username);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("username",loginUser.getUsername());
        //把完整的用户信息存入redis  username作为key
        redisCache.setCacheObject("login:"+loginUser.getUsername(),loginUser);
        return ApiResult.success(map, "登录成功");
    }

    @Override
    public ApiResult<Object> executeLogout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        User loginUser = (User) authentication.getPrincipal();
        String username = loginUser.getId();
        redisCache.deleteObject("login:"+username);
        return ApiResult.success( "退出成功");
    }


    @Override
    public ProfileVO getUserProfile(String id) {
        ProfileVO profile = new ProfileVO();
        User user = baseMapper.selectById(id);
        //拷贝相同字段
        BeanUtils.copyProperties(user, profile);

        return profile;
    }


}

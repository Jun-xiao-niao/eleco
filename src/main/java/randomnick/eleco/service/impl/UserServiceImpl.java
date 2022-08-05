package randomnick.eleco.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import randomnick.eleco.model.vo.ProfileVO;
import randomnick.eleco.service.UserService;
import randomnick.eleco.common.exception.ApiAsserts;
import randomnick.eleco.mapper.UserMapper;
import randomnick.eleco.model.dto.RegisterDTO;
import randomnick.eleco.model.entity.User;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getName()).or().eq(User::getEmail, dto.getEmail());
        User user = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(user)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
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
    public ProfileVO getUserProfile(String id) {
        ProfileVO profile = new ProfileVO();
        User user = baseMapper.selectById(id);
        BeanUtils.copyProperties(user, profile);

        return profile;
    }


}

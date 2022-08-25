package randomnick.eleco.service;


import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.dto.LoginDTO;
import randomnick.eleco.model.dto.RegisterDTO;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.ProfileVO;

import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 注册功能
     *
     * @param dto
     * @return 注册对象
     */
    User executeRegister(RegisterDTO dto);

    /**
     * 获取用户信息
     *
     * @param username
     * @return dbUser
     */
    User getUserByUsername(String username);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);

    /**
     * 用户登录
     *
     * @param dto
     * @return 生成的JWT的token
     */
    ApiResult<Map<String, String>> executeLogin(LoginDTO dto);


    ApiResult<Object> executeLogout();
}
package randomnick.eleco.service;


import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.dto.RegisterDTO;
import randomnick.eleco.model.entity.User;

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

}
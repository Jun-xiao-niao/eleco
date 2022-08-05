package randomnick.eleco.service;

import randomnick.eleco.component.ResponseResult;

public interface LoginService {
    ResponseResult login(String name, String pwd);

    ResponseResult logout();
}

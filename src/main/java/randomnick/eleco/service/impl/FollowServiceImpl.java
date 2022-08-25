package randomnick.eleco.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import randomnick.eleco.mapper.FollowMapper;
import randomnick.eleco.model.entity.Follow;
import randomnick.eleco.service.FollowService;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
}

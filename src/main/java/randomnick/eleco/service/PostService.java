package randomnick.eleco.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.dto.CreateTopicDTO;
import randomnick.eleco.model.entity.Post;
import randomnick.eleco.model.entity.User;
import randomnick.eleco.model.vo.PostVO;

public interface PostService extends IService<Post> {

    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);
    /**
     * 发布
     *
     * @param dto
     * @param principal
     * @return
     */
    Post create(CreateTopicDTO dto, User principal);
}

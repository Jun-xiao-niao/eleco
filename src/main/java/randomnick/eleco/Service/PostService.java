package randomnick.eleco.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.entity.Post;
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
}

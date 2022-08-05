package randomnick.eleco.service;

import com.baomidou.mybatisplus.extension.service.IService;
import randomnick.eleco.model.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    /**
     * 插入标签
     *
     * @param tags
     * @return
     */
    List<Tag> insertTags(List<String> tags);

}

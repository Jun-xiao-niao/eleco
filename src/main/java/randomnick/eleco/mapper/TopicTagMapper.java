package randomnick.eleco.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import randomnick.eleco.model.entity.TopicTag;

@Repository
public interface TopicTagMapper extends BaseMapper<TopicTag> {

}

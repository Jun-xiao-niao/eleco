package randomnick.eleco.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import randomnick.eleco.model.entity.Tag;

@Repository
public interface TagMapper extends BaseMapper<Tag> {

}

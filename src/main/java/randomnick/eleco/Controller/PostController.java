package randomnick.eleco.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import randomnick.eleco.Service.PostService;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.vo.PostVO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private PostService PostService;

    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = PostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

}

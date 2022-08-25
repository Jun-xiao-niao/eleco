package randomnick.eleco.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import randomnick.eleco.common.api.ApiResult;
import randomnick.eleco.model.vo.PostVO;
import randomnick.eleco.service.PostService;

import javax.annotation.Resource;


@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Resource
    private PostService postService;

    @ApiOperation("搜索帖子")
    @GetMapping
    public ApiResult<Page<PostVO>> searchList(@RequestParam("keyword") String keyword,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        Page<PostVO> results = postService.searchByKey(keyword, new Page<>(pageNum, pageSize));
        return ApiResult.success(results);
    }

}



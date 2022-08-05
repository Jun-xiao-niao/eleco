//package randomnick.eleco.Controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import randomnick.eleco.mapper.CollectMapper;
//import randomnick.eleco.mapper.GoodMapper;
//import randomnick.eleco.component.ResponseResult;
//import randomnick.eleco.model.entity.Collect;
//import randomnick.eleco.model.entity.Good;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static randomnick.eleco.component.KeyUtil.getUniqueKey;
//
//@Controller
//@Api("用户收藏")
//@RequestMapping("/collect")
//public class CollectController {
//    @Autowired
//    CollectMapper collectMapper;
//
//    @Autowired
//    GoodMapper goodMapper;
//
//    @ApiOperation("查看所有商品的所有收藏者")
//    @RequestMapping(value = "/findAllCollect", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findAllCollect() {
//        List<Collect> list = collectMapper.selectList(null);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("查找用户收藏")
//    @RequestMapping(value = "/findCollect", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findCollect(@RequestParam("userId") String userId) {
//        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id", userId);
//        List<Collect> list = collectMapper.selectList(wrapper);
//        List<Good> finalList = new ArrayList();
//        for (int i = 0; i < list.size(); i++){
//            Collect c = list.get(i);
//            QueryWrapper<Good> wrapper2 = new QueryWrapper<>();
//            wrapper2.eq("id", c.getGoodId());
//            finalList.add(goodMapper.selectList(wrapper2).get(0));
//        }
//        return new ResponseResult(200,"查询成功",finalList);
//    }
//
//    @ApiOperation("用户添加收藏")
//    @RequestMapping(value = "/postCollect", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseResult postCollect(@RequestParam("userId") String userId,
//                                      @RequestParam("goodId") String goodId) {
//        Collect collect = new Collect(getUniqueKey(),userId,goodId);
//        collectMapper.insert(collect);
//        return new ResponseResult(200,"收藏成功");
//    }
//
//    @ApiOperation("用户删除收藏")
//    @RequestMapping(value = "/deleteCollect", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseResult deleteCollect(@RequestParam("id") String id) {
//        collectMapper.deleteById(id);
//        return new ResponseResult(200,"删除收藏成功");
//    }
//
//}

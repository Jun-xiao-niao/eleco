//package randomnick.eleco.Controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import randomnick.eleco.mapper.GoodMapper;
//import randomnick.eleco.mapper.HistoryMapper;
//import randomnick.eleco.mapper.OrderFormMapper;
//import randomnick.eleco.mapper.UserMapper;
//import randomnick.eleco.component.ResponseResult;
//import randomnick.eleco.model.entity.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//import static randomnick.eleco.component.KeyUtil.getUniqueKey;
//
//@Controller
//@Api("用户操作商品")
//@RequestMapping("/good")
//
//public class GoodController {
//
//    @Autowired
//    GoodMapper goodMapper;
//    @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    OrderFormMapper orderFormMapper;
//
//    @Autowired
//    HistoryMapper historyMapper;
//
//    @Value("C:/Users/Junxiaoniao/Desktop/west2/Picture/")
//    private String imagePath;
//
//    @Value("4934d114v1.qicp.vip/image/")
//    private String webPath;
//
//    @ApiOperation("卖家上传商品，等待审核")
//    @RequestMapping(value = "/uploadGood", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseResult uploadGood(@RequestParam("goodDescrip") String goodDescrip,
//                                     @RequestParam("price") double price,
//                                     @RequestParam("type") String type,
//                                     @RequestParam("sellerId") String sellerId,
//                                     @RequestParam("pictures") MultipartFile[] files) throws IOException {
//        String id = getUniqueKey();
//        Good good = new Good();
//        good.setId(id);
//        good.setGoodDescrip(goodDescrip);
//        good.setPrice(price);
//        good.setType(type);
//        good.setSellerId(sellerId);
//        good.setState("审核中");
//        String pictureUrl = "";
//        //判断file数组不能为空并且长度大于0
//        if (files != null && files.length > 0) {
//            //循环获取file数组中得文件
//            for (int i = 0; i < files.length; i++) {
//                MultipartFile file = files[i];
//                //保存文件
//                if (!file.isEmpty()) {
//                    String fileName = file.getOriginalFilename();//得到文件名
//                    // 解析到文件后缀，判断是否合法
//                    int index = fileName.lastIndexOf(".");
//                    String suffix = null;
//                    if (index == -1 || (suffix = fileName.substring(index + 1)).isEmpty()) {
//                        return new ResponseResult(400, "文件后缀不能为空");
//                    }
//                    // 允许上传的文件后缀列表
//                    Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
//                    if (!allowSuffix.contains(suffix.toLowerCase())) {
//                        return new ResponseResult(400, "非法的文件，不允许的文件类型：" + suffix);
//                    }
//                    String suffixName = "." + suffix;//得到后缀名
//                    String realFileName = fileName.substring(0, fileName.lastIndexOf(suffixName));//得到不包含后缀的文件名
//                    fileName = id + (i + 1) + suffixName;
//                    String fileUrl = imagePath + fileName;
//                    pictureUrl += webPath + fileName + " ";
//                    File tempfile = new File(fileUrl);
//                    if (!tempfile.getParentFile().exists()) {
//                        tempfile.getParentFile().mkdirs();
//                    }
//                    try {
//                        file.transferTo(tempfile);
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//            }
//        }
//        good.setPictureUrl(pictureUrl);
//        goodMapper.insert(good);
//        return new ResponseResult(200, "上传成功");
//    }
//
//    @ApiOperation("卖家修改不通过的商品信息，等待审核")
//    @RequestMapping(value = "/uploadGoodAgain", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult uploadGoodAgain(@RequestParam("goodId") String goodId,
//                                          @RequestParam("goodDescrip") String goodDescrip,
//                                          @RequestParam("price") double price,
//                                          @RequestParam("type") String type,
//                                          @RequestParam("sellerId") String sellerId,
//                                          @RequestParam("pictures") MultipartFile[] files) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//        Good good = goodMapper.selectOne(wrapper);
//
//        good.setGoodDescrip(goodDescrip);
//        good.setPrice(price);
//        good.setType(type);
//        good.setSellerId(sellerId);
//        good.setState("审核中");
//        String pictureUrl = "";
//        //判断file数组不能为空并且长度大于0
//        if (files != null && files.length > 0) {
//            //循环获取file数组中得文件
//            for (int i = 0; i < files.length; i++) {
//                MultipartFile file = files[i];
//                //保存文件
//                if (!file.isEmpty()) {
//                    String fileName = file.getOriginalFilename();//得到文件名
//                    // 解析到文件后缀，判断是否合法
//                    int index = fileName.lastIndexOf(".");
//                    String suffix = null;
//                    if (index == -1 || (suffix = fileName.substring(index + 1)).isEmpty()) {
//                        return new ResponseResult(400, "文件后缀不能为空");
//                    }
//                    // 允许上传的文件后缀列表
//                    Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
//                    if (!allowSuffix.contains(suffix.toLowerCase())) {
//                        return new ResponseResult(400, "非法的文件，不允许的文件类型：" + suffix);
//                    }
//                    String suffixName = "." + suffix;//得到后缀名
//                    String realFileName = fileName.substring(0, fileName.lastIndexOf(suffixName));//得到不包含后缀的文件名
//                    fileName = goodId + (i + 1) + suffixName;
//                    String fileUrl = imagePath + fileName;
//                    pictureUrl += webPath + fileName + " ";
//                    File tempfile = new File(fileUrl);
//                    if (!tempfile.getParentFile().exists()) {
//                        tempfile.getParentFile().mkdirs();
//                    }
//                    try {
//                        file.transferTo(tempfile);
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//            }
//        }
//        good.setPictureUrl(pictureUrl);
//        goodMapper.updateById(good);
//        return new ResponseResult(200, "上传成功");
//    }
//
//    @ApiOperation("查找所有上架中的商品")
//    @RequestMapping(value = "/findSellingGood", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findSellingGood() {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("state", "上架中");
//        List<Good> list = goodMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("查找所有标签")
//    @RequestMapping(value = "/findType", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findAllType() {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.select("type");//指定查询某字段
//        List<Good> list = goodMapper.selectList(wrapper);
//        List finalList = new ArrayList<>();
//        for (Good good : list) {
//            if (!finalList.contains(good.getType())) {
//                finalList.add(good.getType());
//            }
//        }
//        return new ResponseResult(200, "查询成功", finalList);
//    }
//
//    @ApiOperation("通过标签查找商品")
//    @RequestMapping(value = "/findGoodByType", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findGoodByType(@RequestParam("type") String type) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("type", type)
//                .eq("state", "上架中");
//        List<Good> list = goodMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("用户模糊搜索商品")
//    @RequestMapping(value = "/searchGood", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findGood(@RequestParam("goodDescrip") String goodDescrip) {
//
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.like("good_descrip", goodDescrip)
//                .eq("state", "上架中");
//        List<Good> list = goodMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("用户点击进入商品，留下足迹")
//    @RequestMapping(value = "/clickGood", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult clickGood(@RequestParam("userId") String userId,
//                                    @RequestParam("goodId") String goodId) {
//
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//
//        Good good = goodMapper.selectOne(wrapper);
//
//        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
//        wrapper2.eq("id", good.getSellerId());
//        User user = userMapper.selectOne(wrapper2);
//
//        SellGood sellGood = new SellGood(good.getId(), good.getGoodDescrip(), good.getPrice(), good.getType(), good.getSellerId(), user.getUsername(), user.getPictureUrl(), good.getBuyerId(), good.getState(), good.getPictureUrl(), good.getCreateTime(), good.getUpdateTime());
//        List<SellGood> list = new ArrayList();
//        list.add(sellGood);
//        History history = new History();
//        history.setId(getUniqueKey());
//        history.setUserId(userId);
//        history.setGoodId(goodId);
//        historyMapper.insert(history);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("买家购买商品,创造订单")
//    @RequestMapping(value = "/buyGood", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult buyGood(@RequestParam("goodId") String goodId,
//                                  @RequestParam("buyerId") String buyerId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//        Good good = goodMapper.selectOne(wrapper);
//        good.setState("交易中");
//        good.setBuyerId(buyerId);
//        goodMapper.updateById(good);
//
//        OrderForm orderForm = new OrderForm();
//        orderForm.setId(getUniqueKey());
//        orderForm.setGoodId(good.getId());
//        orderForm.setGoodDescrip(good.getGoodDescrip());
//        orderForm.setPrice(good.getPrice());
//        orderForm.setType(good.getType());
//        orderForm.setSellerId(good.getSellerId());
//        orderForm.setBuyerId(good.getBuyerId());
//        orderForm.setState(good.getState());
//        orderForm.setPictureUrl(good.getPictureUrl());
//        orderFormMapper.insert(orderForm);
//
//        return new ResponseResult(200, "开始交易");
//    }
//
//    @ApiOperation("买家/卖家取消订单，系统帮卖家重新上架商品")
//    @RequestMapping(value = "/cancelOrder", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult cancelOrder(@RequestParam("goodId") String goodId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//        Good good = goodMapper.selectOne(wrapper);
//        good.setState("上架中");
//        good.setBuyerId(null);
//        goodMapper.updateById(good);
//
//        QueryWrapper<OrderForm> wrapper2 = new QueryWrapper<>();
//        wrapper.eq("good_id", goodId)
//                .eq("state", "交易中");
//        OrderForm orderForm = orderFormMapper.selectOne(wrapper2);
//        orderForm.setState("交易关闭");
//        orderFormMapper.updateById(orderForm);
//
//        return new ResponseResult(200, "订单取消，商品重新上架");
//    }
//
//    @ApiOperation("卖家下架商品（此时商品还没有买家拍下）")
//    @RequestMapping(value = "/offGood", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult offGood(@RequestParam("goodId") String goodId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//        Good good = goodMapper.selectOne(wrapper);
//        good.setState("商品下架");
//        goodMapper.updateById(good);
//
//        return new ResponseResult(200, "商品下架成功");
//    }
//
//    @ApiOperation("商品交易成功")
//    @RequestMapping(value = "/GoodOver", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult buyGood(@RequestParam("goodId") String goodId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", goodId);
//        Good good = goodMapper.selectOne(wrapper);
//        good.setState("交易成功");
//        int result = goodMapper.updateById(good);
//
//        QueryWrapper<OrderForm> wrapper2 = new QueryWrapper<>();
//        wrapper.eq("good_id", goodId)
//                .eq("state", "交易中");
//        OrderForm orderForm = orderFormMapper.selectOne(wrapper2);
//        orderForm.setState("交易成功");
//        orderFormMapper.updateById(orderForm);
//
//        return new ResponseResult(200, "商品交易成功");
//    }
//
//}

package randomnick.eleco.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import randomnick.eleco.mapper.GoodMapper;
//import randomnick.eleco.mapper.HistoryMapper;
//import randomnick.eleco.mapper.OrderFormMapper;
import randomnick.eleco.mapper.UserMapper;
import randomnick.eleco.Service.LoginService;
import randomnick.eleco.component.KeyUtil;
import randomnick.eleco.component.ResponseResult;
import randomnick.eleco.model.entity.Good;
import randomnick.eleco.model.entity.History;
import randomnick.eleco.model.entity.OrderForm;
import randomnick.eleco.model.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/userrrr")
@Api("用户功能吧")
public class UserControllerrrrr {

    @Autowired
    UserMapper userMapper;
//    @Autowired
//    GoodMapper goodMapper;
//    @Autowired
//    HistoryMapper historyMapper;
//    @Autowired
//    OrderFormMapper orderFormMapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("C:/Users/Junxiaoniao/Desktop/west2/Picture/")
    private String imagePath;

    @Value("4934d114v1.qicp.vip/image/")
    private String webPath;

    @ApiOperation("登录方法")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestParam("username") String name,
                                @RequestParam("password") String pwd,
                                HttpServletResponse response,
                                HttpSession session) {

        return loginService.login(name, pwd);

    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @ApiOperation("退出方法")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult logout() {
        return loginService.logout();

    }

//    @ApiOperation("注册方法")
//    @RequestMapping(value = "/regist", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseResult regist(@RequestParam("username") String name,
//                                 @RequestParam("password") String pwd) {
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("username", name);
//        List<User> list = userMapper.selectList(wrapper);
//        if (list.size() > 0) {
//            return new ResponseResult(409, "注册失败，用户名被占用");
//        }
//
//        String id = KeyUtil.getUniqueKey();
//        pwd = passwordEncoder.encode(pwd);//密码BCryptPasswordEncoder加密
//        User user = new User(id, name, pwd, "user", "", "", 0);
//        userMapper.insert(user);
//        return new ResponseResult(200, "注册成功");
//    }
//
//    @ApiOperation("添加/修改用户联系方式")
//    @RequestMapping(value = "/modifyContact", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult modifyContact(@RequestParam("userId") String userId,
//                                        @RequestParam("contact") String contact) {
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", userId);
//        User user = userMapper.selectOne(wrapper);
//        user.setContact(contact);
//        userMapper.updateById(user);
//        return new ResponseResult(200, "修改成功");
//    }
//
//    @ApiOperation("添加/修改用户头像")
//    @RequestMapping(value = "/modifyPicture", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseResult modifyPicture(@RequestParam("userId") String userId,
//                                        @RequestParam("picture") MultipartFile file) {
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", userId);
//        User user = userMapper.selectOne(wrapper);
//        String pictureUrl = "";
//        //判断file数组不能为空并且长度大于0
//        if (file != null) {
//            //保存文件
//            if (!file.isEmpty()) {
//                String fileName = file.getOriginalFilename();//得到文件名
//                // 解析到文件后缀，判断是否合法
//                int index = fileName.lastIndexOf(".");
//                String suffix = null;
//                if (index == -1 || (suffix = fileName.substring(index + 1)).isEmpty()) {
//                    return new ResponseResult(400, "文件后缀不能为空");
//                }
//                // 允许上传的文件后缀列表
//                Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
//                if (!allowSuffix.contains(suffix.toLowerCase())) {
//                    return new ResponseResult(400, "非法的文件，不允许的文件类型：" + suffix);
//                }
//                String suffixName = "." + suffix;//得到后缀名
//                String realFileName = fileName.substring(0, fileName.lastIndexOf(suffixName));//得到不包含后缀的文件名
//                fileName = userId + suffixName;
//                String fileUrl = imagePath + fileName;  //拼接图片的本地URL
//                pictureUrl += webPath + fileName + " ";  //拼接图片的内网穿透URL
//                File tempfile = new File(fileUrl);
//                if (!tempfile.getParentFile().exists()) {
//                    tempfile.getParentFile().mkdirs();
//                }
//                try {
//                    file.transferTo(tempfile);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//        user.setPictureUrl(pictureUrl);
//        userMapper.updateById(user);
//        return new ResponseResult(200, "修改成功");
//    }
//
//
//    @ApiOperation("用户查看卖出的所有的商品（审核中，审核不通过，上架中，交易中，商品下架，交易成功）")
//    @RequestMapping(value = "/sellerGoods", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult sellerGoods(@RequestParam("sellerId") String sellerId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("seller_id", sellerId);
//        List<Good> list = goodMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("用户查看买入的所有的商品（交易中，交易成功）")
//    @RequestMapping(value = "/buyerGoods", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult buyerGoods(@RequestParam("buyerId") String buyerId) {
//        QueryWrapper<Good> wrapper = new QueryWrapper<>();
//        wrapper.eq("buyer_id", buyerId);
//        List<Good> list = goodMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("用户查看卖出的所有的商品的订单（交易中，交易成功，交易关闭）")
//    @RequestMapping(value = "/sellerOrders", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult sellerOrders(@RequestParam("sellerId") String sellerId) {
//        QueryWrapper<OrderForm> wrapper = new QueryWrapper<>();
//        wrapper.eq("seller_id", sellerId);
//        List<OrderForm> list = orderFormMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("用户查看买入的所有的商品的订单（交易中，交易成功，交易关闭）")
//    @RequestMapping(value = "/buyerOrders", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult buyerOrders(@RequestParam("buyerId") String buyerId) {
//        QueryWrapper<OrderForm> wrapper = new QueryWrapper<>();
//        wrapper.eq("buyer_id", buyerId);
//        List<OrderForm> list = orderFormMapper.selectList(wrapper);
//        return new ResponseResult(200, "查询成功", list);
//    }
//
//    @ApiOperation("查找用户历史记录（参数updateTime为用户浏览时间）")
//    @RequestMapping(value = "/findHistory", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult findCollect(@RequestParam("userId") String userId) {
//        QueryWrapper<History> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id", userId);
//        List<History> list = historyMapper.selectList(wrapper);
//        List<Good> finalList = new ArrayList();
//        for (int i = 0; i < list.size(); i++) {
//            History h = list.get(i);
//            QueryWrapper<Good> wrapper2 = new QueryWrapper<>();
//            wrapper2.eq("id", h.getGoodId());
//            Good g = goodMapper.selectList(wrapper2).get(0);
//            g.setUpdateTime(h.getCreateTime());
//            finalList.add(g);
//        }
//        return new ResponseResult(200, "查询成功", finalList);
//    }

}

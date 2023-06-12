package jmu.controller;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.Orderdetail;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;


    /*登陆测试*/
    @RequestMapping("/login")
    public Result login(@RequestParam String user_id,@RequestParam String user_pwd)
    {
        Userinfo userinfo=userService.login(user_id,user_pwd);
        Integer code=userinfo!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg=userinfo!=null?"登陆成功":"账户名或密码错误!";
        return new Result(code,userinfo,msg);

    }

    /*注册测试*/
    @RequestMapping("/enroll")
    public  Result enroll(@RequestBody Userinfo userinfo)
    {
        boolean flag=userService.enroll(userinfo);
        return new Result(flag?Code.ENROLL_OK:Code.ENROLL_ERR,flag);
    }

    /*查看订单测试*/

    @RequestMapping("/searchOrders")
    public  Result searchOrders(@RequestParam String user_id)
    {
        List<Orders> ordersList=userService.searchOrders(user_id);
        Integer code=ordersList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=ordersList!=null?"":"该用户尚未下单!";
        return new Result(code,ordersList,msg);
    }

    /*查看订单详情测试*/

    @RequestMapping("/searchOrderdetail")
    public  Result searchOrderdetail(@RequestParam String order_id)
    {
        List<Orderdetail> orderdetailList=userService.searchOrderdetail(order_id);
        Integer code=orderdetailList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderdetailList!=null?"":"该用户尚未购买商品!";
        return new Result(code,orderdetailList,msg);
    }



    /*根据商品id和discount计算价格*/
    @RequestMapping("/getItemPrice")
    public  Result   getItemPrice(@RequestParam String item_id)
    {
        Map<String,Object> itemprice=userService.getItemPrice(item_id);
        float price=(float)itemprice.get("item_price");
        float discount=(float)itemprice.get("item_discount");
        double Itemprice=price*discount;
        Integer code=itemprice!=null?Code.GET_OK:Code.GET_ERR;
        String msg=itemprice!=null?"":"未找到该商品!";
        return  new Result(code,itemprice,msg);
    }



    @RequestMapping("/addOrder")
            public Result   addOrders(String item_id)
    {
        Map<String,Object> itemprice=userService.getItemPrice(item_id);
        float price=(float)itemprice.get("item_price");
        float discount=(float)itemprice.get("item_discount");
       float Itemprice=price*discount;
        /*获取当前日志时间*/
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String  create_time=dateFormat.format(date);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        String randomNumber = sb.toString();
        System.out.println("随机生成的十二位数为：" + randomNumber);
        /*随机生成12位数的订单编号*/
        Orders orders=new Orders();
        String user_id=(String)request.getSession().getAttribute("user_id");
        orders.setOrder_id(String.valueOf(randomNumber));
        orders.setUser_id(user_id);
        orders.setCreate_time(create_time);
        orders.setOrder_status("未支付");
        orders.setOrder_totalprice(Itemprice);
        boolean flag=userService.addOrder(orders);

   return new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag);


    }




}

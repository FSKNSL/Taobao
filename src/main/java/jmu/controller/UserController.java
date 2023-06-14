package jmu.controller;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.*;
import org.apache.ibatis.annotations.Update;
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



    /*用户查看个人信息*/
    @RequestMapping("/showUserInfo")
    public Result showUserInfo(String user_id)
    {

        Userinfo userinfo=userService.showUserInfo(user_id);
        Integer code=userinfo!=null?Code.GET_OK:Code.GET_ERR;
        String msg=userinfo!=null?"":"用户信息获取失败!";
        return new Result(code,userinfo,msg);
    }
    /*用户修改个人信息*/
    @RequestMapping("/alterUserInfo")
    public  Result  alterUserInfo(String user_id,String user_nickname,String user_email,String user_tel,String user_pwd)
    {
        Integer rows=userService.alterUserInfo(user_id,user_nickname,user_email,user_tel,user_pwd);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户信息更新成功!":"用户更新失败,请重试!";
        return new Result(code,rows,msg);
    }


    @RequestMapping("/alterAddress")
    public  Result alterAddress(String district_id,String receipt_name,String receipt_tel,String detail_address,String receipt_email,String  address_id)
    {
        Integer rows= userService.alterAddress(district_id,receipt_name,receipt_tel,detail_address,receipt_email,address_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户地址更新成功!":"地址更新失败,请重试!";
        return new Result(code,rows,msg);

    }

    @RequestMapping("/addAddress")
    public  Result addAddress(@RequestBody Address address)
    {
        boolean flag=userService.addAddress(address);
        return new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag);
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
            public Result   addOrders(String item_id,int item_number)
    {
        Map<String,Object> itemprice=userService.getItemPrice(item_id);
        float price=(float)itemprice.get("item_price");
        float discount=(float)itemprice.get("item_discount");
       float Itemprice=item_number*price*discount;
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
        orders.setReceipt_status("未收货");
        orders.setShipment_status("未发货");
        orders.setOrder_totalprice(Itemprice);
        boolean flag=userService.addOrder(orders);
  // return new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag);

        /*添加订单详情操作*/
        Orderdetail orderdetail=new Orderdetail();
        orderdetail.setItem_id(item_id);
        orderdetail.setOrder_id(randomNumber);
        orderdetail.setItem_number(item_number);
        orderdetail.setPay_price(Itemprice);
        orderdetail.setTotal_discount(discount);
        boolean flag2=userService.addOrderdetail(orderdetail);

        return new Result(flag2?Code.INSERT_OK:Code.INSERT_ERR,flag2);


    }

    /*用户支付订单*/
    @RequestMapping("/payOrders")
    public Result  payOrders(String order_id)
    {
       int rows=userService.payOrders(order_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户支付成功!":"用户支付失败,请重试!";
        return new Result(code,rows,msg);

    }

    /*用户对订单评价*/
    @RequestMapping("/doappraiseOrders")

    public  Result  appraiseOrders(  String user_id,String order_id,String appraise_grade,String appraise_content)
    {


        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String  create_time=dateFormat.format(date);

        Appraise appraise=new Appraise();
        appraise.setUser_id(user_id);
        appraise.setOrder_id(order_id);
        appraise.setAppraise_time(create_time);
        appraise.setAppraise_grade(appraise_grade);
        appraise.setAppraise_content(appraise_content);
        boolean flag=userService.appraiseOrders(appraise);
        Integer code=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
        String msg=flag!=false?"订单评价成功,感谢您的参与":"订单未完成评价!";
        return new Result(code,flag,msg);
    }

    /*展示所有商品的信息*/
@RequestMapping("/listAllItems")
    public Result  listAllItems()
{
      List<Orderitem> orderitemList=userService.listAllItems();
      Integer code=orderitemList!=null?Code.GET_OK:Code.GET_ERR;
      String msg=orderitemList!=null?"商品列表页如下":"未查询到商品信息,请刷新页面重试!";
      return  new Result(code,orderitemList,msg);

}

/*显示当前用户购物车信息*/
    @RequestMapping("/listAllCart")
    public Result  listAllCart(String user_id)
    {
        List<Shoppingcart>shoppingcartList=userService.listAllCart(user_id);
        Integer code=shoppingcartList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=shoppingcartList!=null?"购物车信息如下":"购物车空空如也,byd还不快来买";
        return  new Result(code,shoppingcartList,msg);
    }



    /*点击商品加入购物车*/
    @RequestMapping("/addShoppingCart")
    public Result  addShopping(@RequestBody  Shoppingcart shoppingcart)
    {
         boolean flag=userService.addShoppingCart(shoppingcart);
                 return new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag);
    }


    /*对购物车的修改操作*/
    /*主要是对商品数量的修改,和价格的修改*/

    @RequestMapping("/alterShoppingCart")
    public Result alterShoppingCart(int item_number,float item_price,int cart_id)
    {
        float  alter_price=item_number*item_price;
        Integer rows=userService.alterShoppingCart(item_number,alter_price,cart_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"购物车信息更新成功":"购物车信息更新失败,请重试!";
        return new Result(code,rows,msg);
    }

    /*按照传入的cart_id删除一条购物车记录*/
    @RequestMapping("/deleteShoppingcart")
    public Result deleteShoppingcart(int cart_id)
    {
        Integer rows=userService.deleteShoppingcart(cart_id);
        Integer code=rows!=0?Code.DELETE_OK:Code.DELETE_ERR;
        String msg=rows!=0?"购物车删除成功":"购物车信息未成功删除,请重试!";
        return new Result(code,rows,msg);
    }









}

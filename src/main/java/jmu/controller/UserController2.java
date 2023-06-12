package jmu.controller;


import jmu.service.UserService;
import jmu.vo.Appraise;
import jmu.vo.Orderdetail;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/User2")
public class UserController2 {
    @Autowired
    HttpServletRequest request;

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request, @RequestParam String user_id, @RequestParam String user_pwd, Model model)
    {
        Userinfo userinfo=userService.login(user_id,user_pwd);
        Integer code=userinfo!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg=userinfo!=null?"买家登陆成功":"账户名或密码错误";
        Result result=new Result(code,userinfo,msg);
        model.addAttribute("result",result);
        if(userinfo!=null) {
            /*设置session*/
            HttpSession session=request.getSession();
            session.setAttribute("user_id",user_id);
            session.setAttribute("nickname",userinfo.getUser_nickname());
            /*返回买家登陆主界面*/
            return "douserLogin";
        }else {
            return "login";
        }
    }

    @RequestMapping("/dologin")
    public  String doLogin(){return  "login";}


    /*用户注册*/
    @RequestMapping("enroll")
    public String enroll(Userinfo userinfo ,Model model)
    {
        boolean flag=userService.enroll(userinfo);
        String msg=flag!=flag?"注册成功":"注册失败!";
        Result result=new Result(flag?Code.ENROLL_OK:Code.ENROLL_ERR,flag,msg);
        model.addAttribute("result",result);
        model.addAttribute("userinfo",userinfo);
        /*注册成功后返回前端自动跳转到登陆界面*/
        return "doEnroll";

    }

/*用户查看订单*/
    @RequestMapping("/searchOrders")
    public String  searchOrders(@RequestParam String user_id,Model model)
    {
        List<Orders> ordersList=userService.searchOrders(user_id);
        Integer code=ordersList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=ordersList!=null?"":"该用户尚未下单!";
        Result result= new Result(code,ordersList,msg);
        model.addAttribute("result",result);
        model.addAttribute("ordersList",ordersList);
        /*跳转至订单页面*/
        return "searchOrders";
    }

    /*用户查看订单详情*/
    @RequestMapping("/searchOrderdetail")
    public  String  searchOrderdetail(@RequestParam String order_id,Model model)
    {
        List<Orderdetail> orderdetailList=userService.searchOrderdetail(order_id);
        Integer code=orderdetailList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderdetailList!=null?"":"该用户尚未购买商品!";
        Result result=new  Result(code,orderdetailList,msg);
        model.addAttribute("orderdetailList",orderdetailList);
        model.addAttribute("result",result);
        /*跳转至订单详情页面*/
        return  "searchOrderdetail";
    }




    @RequestMapping("/addOrder")
    /*前端页面勾选商品,传递相应的商品id*/
    public   String    addOrders(String item_id,int item_number,Model model)
    {
        Map<String,Object> itemprice=userService.getItemPrice(item_id);
        float price=(float)itemprice.get("item_price");
        float discount=(float)itemprice.get("item_discount");
        float Itemprice=item_number*price*discount;
        System.out.println("itemprice="+Itemprice);
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
        String user_id=(String)request.getSession().getAttribute("user_id");/*获取用户id*/
        orders.setOrder_id(String.valueOf(randomNumber));
        orders.setUser_id(user_id);
        orders.setCreate_time(create_time);
        orders.setOrder_status("未支付");
        orders.setOrder_totalprice(Itemprice);
        boolean flag=userService.addOrder(orders);
         String msg=flag!=false?"用户添加订单成功!":"添加订单失败";

        Result  result1= new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag);

        /*这里用户在商品界面勾选了响应的商品后可点击下单按钮,订单会根据用户的选择来生成相应的订单详情记录*/


        /*自动生成相应的订单详情记录*/
        /*添加订单详情操作*/
        Orderdetail orderdetail=new Orderdetail();
        orderdetail.setItem_id(item_id);
        orderdetail.setOrder_id(randomNumber);
        orderdetail.setItem_number(item_number);
        orderdetail.setPay_price(Itemprice);
        orderdetail.setTotal_discount(discount);
        boolean flag2=userService.addOrderdetail(orderdetail);
        Result result2=new Result(flag2?Code.INSERT_OK:Code.INSERT_ERR,flag2);
        String msg2=flag2!=false?"用户订单详情记录成功!":"订单详情记录失败";


        /*返回到了下订单完成界面*/
        return    "doaddOrders";


    }

    /*用户支付订单*/
    @RequestMapping("/payOrders")
    public String   payOrders(String order_id,Model model)
    {
        int rows=userService.payOrders(order_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户支付成功!":"用户支付失败,请重试!";
        Result result= new Result(code,rows,msg);
        model.addAttribute("result",result);

        /*支付成功界面*/
        return "dopayOrders";

    }


    /*用户评价订单*/

    /*1.用户点击按钮跳转到评价页面*/
    /*需要传入参数:订单编号*/

    @RequestMapping("/doappraiseOrders")
    public String dooappraiseOrders(String order_id,Model model)
    {
        String user_id=(String)request.getSession().getAttribute("user_id");
        /*session自动获取user_id*/
        /*获取当前日志时间*/
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String  create_time=dateFormat.format(date);
        model.addAttribute("user_id",user_id);
        model.addAttribute("order_id",order_id);
        model.addAttribute("create_time",create_time);

        /*这是一个表单,用于用户填写评价*/
        /*将默认参数传递到表单中*/
        return "appraise";

    }
    @RequestMapping("/appraiseOrders")
    public String appraiseOrders(Appraise appraise,Model model)
    {
        boolean flag=userService.appraiseOrders(appraise);
        String msg=flag!=false?"用户评价成功,感谢您的参与":"未成功评价,请重试!";
        Result result=new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag,msg);

        /*这里的是一个返回结果界面*/
        /*可更改为模态框*/
        return  "searchOrders";
    }












}

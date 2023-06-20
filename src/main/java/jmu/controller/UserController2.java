package jmu.controller;


import jmu.service.UserService;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String msg=userinfo!=null?null:"账户名或密码错误";
        Result result=new Result(code,userinfo,msg);
        model.addAttribute("result",result);
        if(userinfo!=null) {
            /*设置session*/
            HttpSession session=request.getSession();
            session.setAttribute("user_id",user_id);
            session.setAttribute("user_pwd",user_pwd);
            session.setAttribute("nickname",userinfo.getUser_nickname());
            /*返回买家登陆主界面*/
            return "douserLogin";
        }else {
            return "login";
        }
    }

    @RequestMapping("/dologin")
    public  String doLogin(Model model){
        model.addAttribute("result",new Result());
        return  "login";
    }
    @RequestMapping("/doenroll")
    public String doenroll(){return "register";}
    /*用户注册*/
    @RequestMapping("enroll")
    public String enroll(Userinfo userinfo ,Model model)
    {
        boolean flag=userService.enroll(userinfo);
        String msg=flag!=false?"注册成功":"注册失败!";
        Result result=new Result(flag?Code.ENROLL_OK:Code.ENROLL_ERR,flag,msg);
        model.addAttribute("result",result);
        model.addAttribute("userinfo",userinfo);
        /*注册成功后返回前端自动跳转到登陆界面*/
        return "login";

    }

    /*用户个人信息查看*/
    /*用户查看个人信息*/
    @RequestMapping("/showUserInfo")
    public String  showUserInfo(Model model)
    {
         String user_id=(String)request.getSession().getAttribute("user_id");
        Userinfo userinfo=userService.showUserInfo(user_id);
        Integer code=userinfo!=null?Code.GET_OK:Code.GET_ERR;
        String msg=userinfo!=null?"":"用户信息获取失败!";
        Result result= new Result(code,userinfo,msg);
        model.addAttribute("result",result);
        return "personCenter";
    }

    /*用户修改个人信息*/
    @RequestMapping("/alterUserInfo")
    public  String   alterUserInfo(String user_nickname,String user_email,String user_tel,String user_pwd,Model model)
    {
        String user_id=(String)request.getSession().getAttribute("user_id");
        Integer rows=userService.alterUserInfo(user_id,user_nickname,user_email,user_tel,user_pwd);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户信息更新成功!":"用户更新失败,请重试!";
        Userinfo userinfo = userService.showUserInfo(user_id);
        Result result= new Result(code,userinfo,msg);
        model.addAttribute("result",result);
        HttpSession session=request.getSession();
        session.setAttribute("user_pwd",userinfo.getUser_pwd());
       /*更新完成后本页面自动刷新*/
       return "personCenter";
    }


    /*用户查看地址信息*/
    @RequestMapping("/showAddress")
    public  String showAddress(Model model)
    {

        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Address>addressList=userService.showAddress(user_id);
        Integer code=addressList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=addressList!=null?"":"该用户当前未添加地址!";
        Result result=new Result(code,addressList,msg);
        /*传入地址信息用于用户新增*/
        List<Province>provinceList=userService.listAllProvince();
        List<City>cityList=userService.listAllCity();
        List<District>districtList=userService.listAllDistrict();
        model.addAttribute("provinceList",provinceList);
        model.addAttribute("cityList",cityList);
        model.addAttribute("districtList",districtList);
        model.addAttribute("user_id",user_id);
        model.addAttribute("result",result);

         /*前往用户地址管理页面*/
        return "showAddress";
    }


/*用户地址信息修改*/
    @RequestMapping("/alterAddress")
    public  String  alterAddress(String district_id,String receipt_name,String receipt_tel,String detail_address,String receipt_email,String  address_id,Model model)
    {
        Integer rows= userService.alterAddress(district_id,receipt_name,receipt_tel,detail_address,receipt_email,address_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户地址更新成功!":"地址更新失败,请重试!";
        Result result=new Result(code,rows,msg);
        /*修改后在原界面*/
        return  "showAddress";

    }


    /*新增收货地址的user_id通过requst自动获取*/
    @RequestMapping("/addAddress")
    public  String  addAddress(Address address,Model model)

    {

        boolean flag=userService.addAddress(address);
        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Address>addressList=userService.showAddress(user_id);
        String msg = flag==true ?"添加成功":"添加失败";
        Result result= new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,addressList,msg);
        model.addAttribute("result",result);

        /*新增地址后返回显示用户收货地址的界面*/
        return "showAddress";

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
    @RequestMapping("/searchOrderDetail")
    public  String  searchOrderdetail(@RequestParam String order_id,Model model)
    {
        List<Orderdetail> orderdetailList=userService.searchOrderdetail(order_id);

        /*根据订单详情的商品id传递商品列表*/
        Integer code=orderdetailList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderdetailList!=null?"":"该用户尚未购买商品!";
        Result result=new  Result(code,orderdetailList,msg);
        model.addAttribute("orderdetailList",orderdetailList);
        model.addAttribute("result",result);
        /*跳转至订单详情页面*/
        return  "searchOrderDetail";
    }

    /*跳转到商品详情页面*/
    @RequestMapping("showItemDetail")
    public String showItemDetail(@RequestParam String item_id,Model model)
    {
        Orderitem orderitem=userService.searchItemByid(item_id);
        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Address>addressList=userService.showAddress(user_id);
        Integer code=orderitem!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderitem!=null?"":"该用户尚未购买商品!";
        Result result=new  Result(code,orderitem,msg);
        model.addAttribute("result",result);
        model.addAttribute("address",addressList);
        return "showItemDetail";
    }



/*详情页面添加订单或添加购物车*/
    @RequestMapping("/addOrder")
    /*前端页面勾选商品,传递相应的商品id*/
    public   String    addOrders(String item_id,int item_number,String detail_address,Model model)
    {
        Map<String,Object> itemprice=userService.getItemPrice(item_id);
        BigDecimal price=(BigDecimal)itemprice.get("item_price");
        BigDecimal discount=(BigDecimal)itemprice.get("item_discount");
        BigDecimal res = price.multiply(BigDecimal.valueOf(item_number));
        BigDecimal Itemprice=discount.multiply(res);
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
        orders.setReceipt_status("未收货");
        orders.setShipment_status("未发货");
        orders.setOrder_totalprice(Itemprice);
        boolean flag=userService.addOrder(orders);
         String msg1=flag!=false?"用户添加订单成功!":"添加订单失败";

        Result  result1= new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,flag,msg1);

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
        String msg2=flag2!=false?"用户订单详情记录成功!":"订单详情记录失败";
        Result result2=new Result(flag2?Code.INSERT_OK:Code.INSERT_ERR,flag2,msg2);
        model.addAttribute("result1",result1);
        model.addAttribute("result2",result2);
        List<Orders> ordersList=userService.searchOrders(user_id);
        Integer code=ordersList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=ordersList!=null?"订单已生成":"该用户尚未下单!";
        Result result= new Result(code,ordersList,msg);
        model.addAttribute("result",result);
        /*返回到了下订单完成界面*/
        return "searchOrders";


    }

    /*用户支付订单*/
    @RequestMapping("/payOrders")
    public String   payOrders(@RequestParam String order_id,Model model)
    {
        String user_id = (String)request.getSession().getAttribute("user_id");
        int rows=userService.payOrders(order_id);
        List<Orders> ordersList=userService.searchOrders(user_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"用户支付成功!":"用户支付失败,请重试!";
        /*用户支付后会产生一条支付记录*/
        /*往pay中插入一条信息*/
        /*获取当前日志时间*/
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String  create_time=dateFormat.format(date);
        Pay pay=new Pay();
        pay.setOrder_id(order_id);
        pay.setUser_id(user_id);
        pay.setPay_date(create_time);
        /*生成随机的十位数字符单编号*/
        Random random = new Random();
        int randomNum = 0;
        int min = 100000000; // 最小值为100000000
        int max = 999999999; // 最大值为999999999
        randomNum = random.nextInt(max - min + 1) + min; // 生成随机数
        String[] payments = {"微信支付", "支付宝支付", "银联支付"}; // 支付方式数组
        Random random2 = new Random();
        int index = random2.nextInt(payments.length); // 生成一个随机下标
        String payment = payments[index]; // 根据下标获取对应的支付方式
       pay.setPay_method(payment);
        pay.setPay_number(String.valueOf(randomNum));
        Result result= new Result(code,ordersList,msg);
        model.addAttribute("result",result);

        /*支付成功界面*/
        return "searchOrders";

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
    @RequestMapping("/main")
    public String main(Model model){
        List<Orderitem> orderitemList=userService.listAllItems();
        Integer code=orderitemList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderitemList!=null?"商品列表页如下":"未查询到商品信息,请刷新页面重试!";
        Result  result= new Result(code,orderitemList,msg);
        model.addAttribute("result2",result);
        /*跳转到商品列表网页*/
        return "main";
    }
    /*展示所有商品界面*/
    @RequestMapping("/showAllItems")
    public  String showAllItems(Model model)
    {
        /*商品列表页信息*/
        List<Orderitem> orderitemList=userService.listAllItems();
        Integer code=orderitemList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderitemList!=null?"商品列表页如下":"未查询到商品信息,请刷新页面重试!";
        Result  result= new Result(code,orderitemList,msg);
        model.addAttribute("result2",result);
        /*跳转到商品列表网页*/
        return "showAllItems";
    }

    /*用户查看购物车*/

    @RequestMapping("/listAllCart")
    public String   listAllCart(Model model)
    {
        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Shoppingcart>shoppingcartList=userService.listAllCart(user_id);
        Integer code=shoppingcartList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=shoppingcartList!=null?"":"购物车空空如也,byd还不快来买";
        Result result=  new Result(code,shoppingcartList,msg);
        model.addAttribute("result",result);
        /*跳转至购物车界面,在此界面能够结算或返回商品页面继续浏览*/
        return "shoppingCart";
    }


    /*在商品详情页面*/
    /*点击商品加入购物车*/
    /*在商品列表页添加购物车,应该传入orderitem的一些信息*/
    @RequestMapping("/addShoppingCart")
    public String   addShopping(@RequestParam String item_id,Model model)
    {
        String user_id=(String)request.getSession().getAttribute("user_id");
        /*session自动获取user_id*/
        Orderitem orderitem = userService.searchItemByid(item_id);
        Shoppingcart shoppingcart=new Shoppingcart();
        shoppingcart.setUser_id(user_id);
        shoppingcart.setItem_id(item_id);
        shoppingcart.setItem_url(orderitem.getItem_url());
        shoppingcart.setItem_price(orderitem.getItem_price());
        /*打折后实际付款价格*/
        shoppingcart.setItem_name(orderitem.getItem_name());
        shoppingcart.setItem_number(1);
        /*商品数量默认为1,进入购物车界面可通过点击修改*/
        boolean flag=userService.addShoppingCart(shoppingcart);
        String msg = flag!=false ?"加入成功":"加入失败";
        orderitem=userService.searchItemByid(item_id);
        Result result= new Result(flag?Code.INSERT_OK:Code.INSERT_ERR,orderitem,msg);
        model.addAttribute("result",result);

        /*这里的返回页面前端可自定义,可选择去购物车结算还是继续购物*/
        return "showItemDetail";
    }



    /*在购物车界面的修改操作*/
    /*主要是对商品数量的的修改*/

    @RequestMapping("/alterShoppingCart")
    public String alterShoppingCart(@RequestParam(value = "item_number", required = true) int item_number,@RequestParam(value = "cart_id", required = true) int cart_id,Model model)
    {
        Integer rows=userService.alterShoppingCart(item_number,cart_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"":"购物车信息更新失败,请重试!";
        Result result = new Result(code,rows,msg);
        model.addAttribute("result",result);
        /*修改完成后依然在购物车界面,提示信息?*/
        return  "redirect:/listAllCart";
    }

    /*按照传入的cart_id删除一条购物车记录*/
    @RequestMapping("/deleteShoppingcart")
    public String  deleteShoppingcart(@RequestParam int cart_id,Model model)
    {
        Integer rows=userService.deleteShoppingcart(cart_id);
        Integer code=rows!=0?Code.DELETE_OK:Code.DELETE_ERR;
        String msg=rows!=0?"购物车删除成功":"购物车信息未成功删除,请重试!";
        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Shoppingcart>shoppingcartList=userService.listAllCart(user_id);
        Result result= new Result(code,shoppingcartList,msg);
        model.addAttribute("result",result);
        /*删除完成后依然在购物车界面,提示信息?*/
        return "shoppingCart";
    }


    /*用户查看自己的支付记录*/
    @RequestMapping("/listAllPay")
    public String listAllPay(Model model)
    {
        String user_id=(String)request.getSession().getAttribute("user_id");
        List<Pay>payList=userService.listAllPay(user_id);
        Integer code=payList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=payList!=null?"":"该用户尚未有任何支付记录!";
        Result result=new Result(code,payList,msg);
        model.addAttribute("result",result);

        /*返回的页面自定义*/
        return  "";
    }

    //头像
    @RequestMapping("/updateAvatar")
    public String updateAvatar(@RequestParam MultipartFile file,Model model) throws IOException {
        String user_id=(String)request.getSession().getAttribute("user_id");
        String fileName = file.getOriginalFilename();
        //获取文件后缀名。也可以在这里添加判断语句，规定特定格式的图片才能上传，否则拒绝保存。
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Integer rows;
        if (!suffixName.equals(".jpg")&& !suffixName.equals(".png")){
            rows=0;
        }else {
        //为了避免发生图片替换，这里使用了文件名重新生成
        fileName = UUID.randomUUID()+suffixName;
        String path = ResourceUtils.getURL("classpath:").getPath()+"static/image/";
        file.transferTo(new File(path+fileName));
        String datapath = "/image/"+fileName;
        rows = userService.updateAvatar(user_id,datapath);
        }
        Userinfo userinfo=userService.showUserInfo(user_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"头像上传成功":"文件格式错误";
        Result result = new Result(code,userinfo,msg);
        model.addAttribute("result",result);
        return "personCenter";
    }


    /*对购物车商品批量下单,需要传入参数cart_id*/
    /*生成一个订单操作,并且一个订单对应响应商品数量的订单详情*/
    @RequestMapping("/PayShoppingCart")
    public String  PayShoppingCart(@RequestParam(value = "cart", required = true)int  [] cart,@RequestParam(value = "total_price", required = true)BigDecimal order_totalprice ,Model model)




    {
        /*Step1:创建订单*/
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
        orders.setReceipt_status("未收货");
        orders.setShipment_status("未发货");
        /*接收前端传递的total_price价格*/
        orders.setOrder_totalprice(order_totalprice);
        boolean flag1=userService.addOrder(orders);


        /*Step2:根据cart_id的数量创建多个订单详情*/
        int length= cart.length;
        for(int i=0;i<length;i++)
        {
            Orderdetail orderdetail=new Orderdetail();
            String item_id=userService.selectItemidByCartId(cart[i]);
            int item_number=userService.selectItemnumberByCartId(cart[i]);
            Map<String,Object> itemprice=userService.getItemPrice(item_id);
            BigDecimal price=(BigDecimal)itemprice.get("item_price");
            BigDecimal discount=(BigDecimal)itemprice.get("item_discount");
            BigDecimal res = price.multiply(BigDecimal.valueOf(item_number));
            BigDecimal Itemprice=discount.multiply(res);
            orderdetail.setItem_id(item_id);
            orderdetail.setOrder_id(randomNumber);/*下面的所有订单详情对应一个订单编号*/
            orderdetail.setItem_number(item_number);
            orderdetail.setPay_price(Itemprice);
            orderdetail.setTotal_discount(discount);
            boolean flag=userService.addOrderdetail(orderdetail);
            boolean flag2=userService.deleteshoppingcartByid(cart[i]);


        }


        /*购物车完成批量下单返回页面*/
        return "shoppingCart";
    }




    /*对商品的模糊查询*/
    @RequestMapping("/searchItemByKeyword")
    public String  searchItemByKeyword(String keyword,Model model)
    {
        List<Orderitem>orderitemList=userService.selectOrderitemByKeyword(keyword);
        Integer code=orderitemList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=orderitemList!=null?"":"没有查询到相关商品,请等待新商品上架!";
        Result result=new Result(code,orderitemList,msg);
        model.addAttribute("result",result);
        return "searchItemByKeyword";
    }



    /*展示某买家在各个商品某月的消费报表*/

    @RequestMapping("/getUserTotalSales")
    public String  getUserTotalSales(String user_id,Model model)
    {
        List<Map<String,Object>>getUserTotalSales=userService.getUserTotalSales(user_id);
        Integer code=getUserTotalSales!=null?Code.GET_OK:Code.GET_ERR;
        String msg=getUserTotalSales!=null?"":"未取得该用户的商品消费报表!请重试";
        Result result=new Result(code,getUserTotalSales,msg);
        model.addAttribute("result",result);
        /*跳转到用户消费报表*/
        return "getUserTotalSales";

    }





}

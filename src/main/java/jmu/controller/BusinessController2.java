package jmu.controller;


import jmu.service.BusinessService;
import jmu.service.UserService;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/Business2")
public class BusinessController2 {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private UserService userService;
    @RequestMapping("/login2")
    public String login(HttpServletRequest request, @RequestParam String business_id, @RequestParam String business_pwd, Model model)
    {
       Business business =businessService.login(business_id,business_pwd);
        Integer code=business!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg=business!=null?null:"账户名或密码错误";
        Result result=new Result(code,business,msg);
        model.addAttribute("result",result);
        if(business!=null) {
            /*设置session*/
            HttpSession session=request.getSession();
            session.setAttribute("business_id",business_id);
            session.setAttribute("business_pwd",business_pwd);
            session.setAttribute("store_name",business.getStore_name());
            /*返回商家家登陆主界面*/
            return "businessCenter";
        }else {
            return "blogin";
        }
    }
    @RequestMapping("/dologin2")
    public  String doLogin(Model model){
        model.addAttribute("result",new Result());
        return  "blogin";
    }


    /*商家查看订单*/
    @RequestMapping("/showOrdersByBusiness_id")
    public  String showOrdersByBusiness_id(Model model)
    {

        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orderdetail> ordersList=businessService.showOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"":"订单空空如也";
        Result result=new Result(code, ordersList,msg);
        model.addAttribute("result",result);
        /*商家查看所有挑选了该商家店铺的所有订单*/
        return "searchBusinessOrders";

    }

    /*商家根据订单状态做分类*/
    /*已发货的订单*/
    @RequestMapping("/showShippedOrdersByBusiness_id")
    public  String  showShippedOrdersByBusiness_id(Model model)
    {
        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orders> ordersList=businessService.showShippedOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"查询到如下已发货订单记录!":"您没有已发货的订单";
        Result result= new Result(code, ordersList,msg);
        model.addAttribute("result",result);

        /*标签形式切换订单类型*/
        return  "showbusinessByBusiness_id";
    }


    /*未发货订单*/
    @RequestMapping("/showUnshippedOrdersByBusiness_id")
    public  String  showUnShippedOrdersByBusiness_id(Model model)
    {
        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orders> ordersList=businessService.showUnshippedOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"查询到如下未发货订单记录!":"您没有未发货的订单";
        Result result= new Result(code, ordersList,msg);
        model.addAttribute("result",result);

        /*标签形式切换订单类型*/
        return  "showbusinessByBusiness_id";
    }



    /*对未发货订单执行发货操作*/
    @RequestMapping("/ShipOrder")
    public String  alterShipmentStatus(@RequestParam String orderdetail_id,@RequestParam String order_id,Model model)
    {

        /*1.修改订单状态*/
        Integer rows=businessService.alterShipmentStatus(orderdetail_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"发货成功!":"未完成发货!";
        Result result= new Result(rows,code,msg);
        /*2.派送运单号*/
        /*获取订单id,获取商家id,选择运输公司名称,选择用户收获详细地址,自动生成派送日期,自动生成六位订单编号*/
        String   business_id=(String)request.getSession().getAttribute("business_id");
        String user_id=businessService.findUseridByOrderid(order_id);
        List<String>detailAddressList=businessService.showDetailAddressByUserid(user_id);
        List<Couriercompanies>couriercompaniesList=businessService.listAllCompanies();
        /*获取当前日志时间*/
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String  create_time=dateFormat.format(date);
        Random rand = new Random();
        int num1 = rand.nextInt(100);
        int num2 = rand.nextInt(100);
        int num3 = rand.nextInt(100);
        String waybill_id = String.format("%02d-%02d-%02d", num1, num2, num3);
        model.addAttribute("order_id",order_id);
        model.addAttribute("business_id",business_id);
        model.addAttribute("company_name",couriercompaniesList);/*下拉框中获取公司的名称*/
        model.addAttribute("ship_address",detailAddressList);
        model.addAttribute("ship_date",create_time);
        model.addAttribute("waybill_id",waybill_id);


        /*修改订单状态*/



        /*这里跳转到发货的表单或模态框中，传递必要的参数生成运单信息*/
        return "shipOrder";

    }

    /*点击确定按钮,确定派送信息,并生成运单和快递单*/
    @RequestMapping("/addShipment")
    public String  addShipment(Shipment shipment,Model model)
    {
        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orderdetail> ordersList=businessService.showOrdersByBusiness_id(business_id);
        /*生成快递单*/
        boolean flag=businessService.AddShipment(shipment);
        Integer code=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
        String msg=flag!=false?"发货成功":"发货失败!";
        Result result= new Result(code,ordersList,msg);
        model.addAttribute("result",result);

        /*自动生成运单*/
        Waybill waybill=new Waybill();
        waybill.setWaybill_id(shipment.getWaybill_id());
        waybill.setCompany_id(businessService.findCompanyidByname(shipment.getCompany_name()));
        waybill.setWaybill_status("运输中");
        boolean flag2=businessService.AddWaybill(waybill);
        Integer code2=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
        String msg2=flag2!=false?"已生成运单":"运单生成失败!";
        Result result2= new Result(code2,flag2,msg2);
        model.addAttribute("result2",result2);

        /*标签形式切换订单类型*/
        return  "searchBusinessOrders";

    }

    /*商家增加商品*/
    @RequestMapping("/addOrderitem")
    public String  addOrderitem(Orderitem orderitem,MultipartFile file, Model model) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID()+suffixName;
        String path = ResourceUtils.getURL("classpath:").getPath()+"static/image/";
        file.transferTo(new File(path+fileName));
        String datapath = "/image/"+fileName;
        orderitem.setItem_url(datapath);
        boolean flag=businessService.addOrderitem(orderitem);
        Integer code=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
        String msg=flag!=false?"添加商品成功!":"添加商品失败!";
        String business=(String)request.getSession().getAttribute("business_id");
        List<Orderitem> orderitemList=businessService.showOrderitemByBusinessid(business);
        Result result=new Result(code,orderitemList,msg);
        model.addAttribute("result",result);

        /*返回到商家查看自己店铺所对应的订单商品界面*/
        return  "showOrderItemById";
    }



    /*商家展示所有商品界面*/
    @RequestMapping("/showOrderitemByBusinessid")
    public String showOrderitemBusinessid(Model model)
    {
        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orderitem> orderitemList=businessService.showOrderitemByBusinessid(business_id);
        Integer code = orderitemList!=null?Code.GET_OK:Code.GET_ERR;
        String msg = orderitemList!=null?"":"您还未发布商品";
        Result result = new Result(code,orderitemList,msg);
        model.addAttribute("result",result);
        return "showOrderItemById";
    }


    /*展示某商店各个商品的销售报表*/
    /*柱状统计图示例*/
    @RequestMapping("/getBusinessTotalSales")
    public   String  getBusinessTotalSales(Model model)
    {
        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Map<String,Object>>businessTotalSales=businessService.getBusinessTotalSales(business_id);
        Integer code=businessTotalSales!=null?Code.GET_OK:Code.GET_ERR;
        String msg=businessTotalSales!=null?"":"未取得商店商品消费报表!请重试";
       Result result =new Result(code,businessTotalSales,msg);
       model.addAttribute("result",result);

       /*跳转到商品的销售报表界面*/
       return  "getBusinessTotalSales";
    }






















}

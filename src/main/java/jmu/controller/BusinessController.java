package jmu.controller;


import jmu.mapper.BusinessMapper;
import jmu.service.BusinessService;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    @RequestMapping("login")
    public Result login(@RequestParam String business_id,@RequestParam String business_pwd)
    {
        Business business=businessService.login(business_id,business_pwd);
        Integer code=business!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg=business!=null?"登陆成功!":"账户名或者密码错误!";
        return new Result(code,business,msg);
    }

    @RequestMapping("/showOrdersByBusiness_id")
    public  Result showOrdersByBusiness_id(String business_id)
    {
        List<Orders> ordersList=businessService.showOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"查询到如下订单记录!":"订单空空如也";
        return new Result(code, ordersList,msg);
    }
    @RequestMapping("/showShippedOrdersByBusiness_id")
    public  Result showShippedOrdersByBusiness_id(String business_id)
    {
        List<Orders> ordersList=businessService.showShippedOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"查询到如下已发货订单记录!":"您没有已发货的订单";
        return new Result(code, ordersList,msg);
    }

    @RequestMapping("/alterShipmentStatus")
    public  Result alterShipmentStatus(String order_id)
    {
        Integer rows=businessService.alterShipmentStatus(order_id);
        Integer code=rows!=0?Code.UPDATE_OK:Code.UPDATE_ERR;
        String msg=rows!=0?"发货成功!":"未完成发货!";
        return new Result(rows,code,msg);
    }

    @RequestMapping("/showDetailAddressByUserid")
    public   Result showDetailAddressByUserid(String user_id)
    {

        List<String>detailAddressList=businessService.showDetailAddressByUserid(user_id);
        Integer code=detailAddressList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=detailAddressList!=null?"用户详细地址如下":"获取用户地址失败!";
        return new Result(code,detailAddressList,msg);
    }

    @RequestMapping("/listAllCompanies")
    public Result listAllCompanies()
    {
        List<Couriercompanies>couriercompaniesList=businessService.listAllCompanies();
        Integer code=couriercompaniesList!=null?Code.GET_OK:Code.GET_ERR;
        String msg=couriercompaniesList!=null?"物流公司信息如下":"未查找到物流公司!";
        return new Result(code,couriercompaniesList,msg);
    }

    @RequestMapping("/addShipment")
    public Result addShipment(@RequestBody  Shipment shipment)
    {
         boolean flag=businessService.AddShipment(shipment);
         Integer code=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
         String msg=flag!=false?"已生成运单":"运单生成失败!";
        // return new Result(code,flag,msg);
        /*自动生成运单*/
        Waybill waybill=new Waybill();
        waybill.setWaybill_id(shipment.getWaybill_id());
        waybill.setCompany_id(businessService.findCompanyidByname(shipment.getCompany_name()));
        waybill.setWaybill_status("运输中");
        boolean flag2=businessService.AddWaybill(waybill);
        Integer code2=flag!=false?Code.INSERT_OK:Code.INSERT_ERR;
        String msg2=flag2!=false?"已生成运单":"运单生成失败!";
       return  new Result(code2,flag2,msg2);


    }






}

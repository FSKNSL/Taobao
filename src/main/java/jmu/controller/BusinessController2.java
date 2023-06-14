package jmu.controller;


import jmu.service.BusinessService;
import jmu.service.UserService;
import jmu.vo.Business;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Business2")
public class BusinessController2 {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private BusinessService businessService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request, @RequestParam String business_id, @RequestParam String business_pwd, Model model)
    {
       Business business =businessService.login(business_id,business_pwd);
        Integer code=business!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg=business!=null?"商家登陆成功":"账户名或密码错误";
        Result result=new Result(code,business,msg);
        model.addAttribute("result",result);
        if(business!=null) {
            /*设置session*/
            HttpSession session=request.getSession();
            session.setAttribute("business_id",business_id);
            session.setAttribute("store_name",business.getStore_name());
            /*返回商家家登陆主界面*/
            return "doBusinessLogin";
        }else {
            return "login";
        }
    }
    @RequestMapping("/dologin")
    public  String doLogin(Model model){
        model.addAttribute("result",new Result());
        return  "login";
    }


    /*商家查看订单*/
    @RequestMapping("/showOrdersByBusiness_id")
    public  String showOrdersByBusiness_id(Model model)
    {

        String business_id=(String)request.getSession().getAttribute("business_id");
        List<Orders> ordersList=businessService.showOrdersByBusiness_id(business_id);
        Integer code= ordersList!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        String msg= ordersList!=null?"查询到如下订单记录!":"订单空空如也";
        Result result=new Result(code, ordersList,msg);
        model.addAttribute("result",result);
        /*商家查看所有挑选了该商家店铺的所有订单*/
        return "showOrdersByBusiness_id";

    }


}

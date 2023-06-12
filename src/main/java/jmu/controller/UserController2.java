package jmu.controller;


import jmu.service.UserService;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        Userinfo  userinfo=userService.login(user_id,user_pwd);
        Integer code=userinfo!=null?Code.LOGIN_OK:Code.LOGIN_ERR;
        boolean flag=userinfo!=null?true:false;
        String msg=userinfo!=null?"买家登陆成功":"账户名或密码错误";
        if(userinfo==null)
            /*返回登陆失败页面*/
            return  "loginerror";
        Result result=new Result(flag?Code.LOGIN_OK:Code.LOGIN_ERR,flag,msg);

        if(code.equals(Code.LOGIN_OK))
        {
            /*设置session*/
            HttpSession session=request.getSession();
            session.setAttribute("user_id",user_id);
            session.setAttribute("user_pwd",user_pwd);
        }
        model.addAttribute("result",result);
        model.addAttribute("userinfo",userinfo);
        /*返回买家登陆主界面*/
        return "douserLogin";
    }


    @RequestMapping("/dologin")
    public  String doLogin(){return  "login";}

}

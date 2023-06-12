package jmu.controller;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

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



}

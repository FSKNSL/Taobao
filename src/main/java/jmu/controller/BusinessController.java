package jmu.controller;


import jmu.mapper.BusinessMapper;
import jmu.service.BusinessService;
import jmu.vo.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}

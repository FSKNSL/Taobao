package jmu.service;


import jmu.vo.Userinfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    public Userinfo login(String user_id,String user_pwd);/*登陆*/

    public   boolean enroll(Userinfo userinfo);/*注册*/
}

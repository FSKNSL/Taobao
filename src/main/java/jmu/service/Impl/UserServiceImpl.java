package jmu.service.Impl;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserMapper userMapper;
    @Override
    public Userinfo login(String user_id, String user_pwd) {
        return userMapper.login(user_id,user_pwd);
    }

    @Override
    public boolean enroll(Userinfo userinfo) {
        userMapper.enroll(userinfo);
        return  true;
    }
}

package jmu.service.Impl;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.Appraise;
import jmu.vo.Orderdetail;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<Orders> searchOrders(String user_id) {
        return userMapper.searchOrders(user_id);
    }

    @Override
    public List<Orderdetail> searchOrderdetail(String order_id) {
        return userMapper.searchOrderdetail(order_id);
    }

    @Override
    public boolean addOrder(Orders orders) {

        userMapper.addOrder(orders);
        return true;

    }


    @Override
    public Map<String, Object> getItemPrice(String item_id) {
        return  userMapper.getItemPrice(item_id);
    }

    @Override
    public boolean addOrderdetail(Orderdetail orderdetail) {
        userMapper.addOrderdetail(orderdetail);
        return true;
    }

    @Override
    public int payOrders(String orders_id) {
        return   userMapper.payOrders(orders_id);
    }

    @Override
    public boolean appraiseOrders(Appraise appraise) {
        userMapper.appraiseOrders(appraise);
        return true;
    }
}

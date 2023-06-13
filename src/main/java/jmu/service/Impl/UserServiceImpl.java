package jmu.service.Impl;


import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.*;
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
    public Userinfo showUserInfo(String user_id) {
        return userMapper.showUserInfo(user_id);
    }

    @Override
    public int alterUserInfo(String user_id,String user_nickname, String user_email, String user_tel, String user_pwd) {
        return userMapper.alterUserInfo(user_id,user_nickname,user_email,user_tel,user_pwd);
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

    @Override
    public List<Orderitem> listAllItems() {
        return userMapper.listAllItems();
    }

    @Override
    public List<Shoppingcart> listAllCart(String user_id) {
        return  userMapper.listAllCart(user_id);
    }

    @Override
    public boolean addShoppingCart(Shoppingcart shoppingcart) {
        userMapper.addShoppingCart(shoppingcart);
        return true;
    }

    @Override
    public int alterShoppingCart(int item_number, float item_price, int cart_id) {
        return userMapper.alterShoppingCart(item_number,item_price,cart_id);
    }

    @Override
    public int deleteShoppingcart(int cart_id) {
        return userMapper.deleteShoppingcart(cart_id);
    }
}

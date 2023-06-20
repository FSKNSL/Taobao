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
    public List<Address> showAddress(String user_id) {
        return userMapper.showAddress(user_id);
    }

    @Override
    public int alterAddress(String district_id, String receipt_name, String receipt_tel, String detail_address, String receipt_email, String address_id) {
        return userMapper.alterAddress(district_id,receipt_name,receipt_tel,detail_address,receipt_email,address_id);
    }

    @Override
    public List<Province> listAllProvince() {
        return userMapper.listAllProvince();
    }

    @Override
    public List<City> listAllCity() {
        return userMapper.listAllCity();
    }

    @Override
    public List<District> listAllDistrict() {
        return userMapper.listAllDistrict();
    }

    @Override
    public boolean addAddress(Address address) {
        userMapper.addAddress(address);
        return true;
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
    public Orderitem searchItemByid(String item_id) {
        return userMapper.searchItemByid(item_id);
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
    public boolean payOrders2(Pay pay) {
        userMapper.payOrders2(pay);
        return true;
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
    public int alterShoppingCart(int item_number,  int cart_id) {
        return userMapper.alterShoppingCart(item_number,cart_id);
    }

    @Override
    public int deleteShoppingcart(int cart_id) {
        return userMapper.deleteShoppingcart(cart_id);
    }

    @Override
    public List<Pay> listAllPay(String user_id) {
        return userMapper.listAllPay(user_id);
    }

    @Override
    public int updateAvatar(String user_id,String user_avatar) {
        return userMapper.updateAvatar(user_id,user_avatar);
    }

    @Override
    public String selectItemidByCartId(int cart_id) {
        return userMapper.selectItemidByCartId(cart_id);
    }

    @Override
    public int selectItemnumberByCartId(int cart_id) {
        return userMapper.selectItemnumberByCartId(cart_id);
    }

    @Override
    public List<Orderitem> selectOrderitemByKeyword(String keyword) {
        return userMapper.selectOrderitemByKeyword(keyword);
    }
}

package jmu.service;


import jmu.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface UserService {

    public Userinfo login(String user_id,String user_pwd);/*登陆*/

    public   boolean enroll(Userinfo userinfo);/*注册*/

    public  Userinfo showUserInfo(String user_id);/*用户查看个人信息*/

    public   int  alterUserInfo(String user_id,String user_nickname,String user_email,String user_tel,String user_pwd);/*修改个人信息*/

    public List<Orders>  searchOrders(String user_id);/*查看订单*/


    public  List<Orderdetail>searchOrderdetail(String order_id);/*查看订单详情*/

    public    boolean addOrder(Orders orders);/*用户添加订单*/
    public Map<String,Object> getItemPrice(@Param("item_id") String item_id);/*按照商品id返回商品价格*/

    public   boolean addOrderdetail(Orderdetail orderdetail);/*订单详情添加,随下单过程同时添加*/


    public   int  payOrders(String orders_id);/*用户支付*/

    public  boolean appraiseOrders(Appraise appraise);/*用户对订单评价*/

    public  List<Orderitem>listAllItems();/*展示所有商品信息*/

    public   List<Shoppingcart>listAllCart(String user_id);/*查看购物车*/

    public boolean addShoppingCart(Shoppingcart shoppingcart);/*点击商品添加购物车*/

    public  int  alterShoppingCart(int item_number,float item_price,int cart_id);/*修改购物车商品数量*/

    public int deleteShoppingcart(int cart_id);/*删除一条购物车记录*/

}

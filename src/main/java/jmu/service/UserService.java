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


    public  List<Address> showAddress(String user_id);/*查看地址信息*/

    /*根据当前地址id修改地址信息*/
    public  int  alterAddress(String district_id,String receipt_name,String receipt_tel,String detail_address,String receipt_email,String address_id);


    public List<Province>listAllProvince();

    public List<City>listAllCity();

    public  List<District>listAllDistrict();


    public   boolean addAddress(Address address);/*用户新增收货地址*/


    public List<Orders>  searchOrders(String user_id);/*查看订单*/


    public  List<Orderdetail>searchOrderdetail(String order_id);/*查看订单详情*/

    public Orderitem searchItemByid(String item_id);/*根据详情的商品id查看商品信息*/

    public    boolean addOrder(Orders orders);/*用户添加订单*/
    public Map<String,Object> getItemPrice(@Param("item_id") String item_id);/*按照商品id返回商品价格*/

    public   boolean addOrderdetail(Orderdetail orderdetail);/*订单详情添加,随下单过程同时添加*/


    public   int  payOrders(String orders_id);/*用户支付*/

    public boolean  payOrders2(Pay pay);/*支付后的订单记录,自动生成*/

    public  boolean appraiseOrders(Appraise appraise);/*用户对订单评价*/

    public  List<Orderitem>listAllItems();/*展示所有商品信息*/

    public   List<Shoppingcart>listAllCart(String user_id);/*查看购物车*/

    public boolean addShoppingCart(Shoppingcart shoppingcart);/*点击商品添加购物车*/

    public  int  alterShoppingCart(int item_number,int cart_id);/*修改购物车商品数量*/

    public int deleteShoppingcart(int cart_id);/*删除一条购物车记录*/

    public   List<Pay> listAllPay(String user_id);/*用户查看自己的支付记录*/

    public int updateAvatar(String user_id,String user_avatar); /*修改头像*/


    public String   selectItemidByCartId(int cart_id);

    public   int   selectItemnumberByCartId(int cart_id);

    public List<Orderitem> selectOrderitemByKeyword(String keyword);

    public  boolean   deleteshoppingcartByid(int  cart_id);

    public List<Map<String, Object>> getUserTotalSales(@Param("user_id") String user_id);


}

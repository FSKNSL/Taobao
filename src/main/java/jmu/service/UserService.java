package jmu.service;


import jmu.vo.Appraise;
import jmu.vo.Orderdetail;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface UserService {

    public Userinfo login(String user_id,String user_pwd);/*登陆*/

    public   boolean enroll(Userinfo userinfo);/*注册*/

    public List<Orders>  searchOrders(String user_id);/*查看订单*/


    public  List<Orderdetail>searchOrderdetail(String order_id);/*查看订单详情*/

    public    boolean addOrder(Orders orders);/*用户添加订单*/
    public Map<String,Object> getItemPrice(@Param("item_id") String item_id);/*按照商品id返回商品价格*/

    public   boolean addOrderdetail(Orderdetail orderdetail);/*订单详情添加,随下单过程同时添加*/


    public   int  payOrders(String orders_id);/*用户支付*/

    public  boolean appraiseOrders(Appraise appraise);/*用户对订单评价*/

}

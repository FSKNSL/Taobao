package jmu.mapper;

import com.sun.org.apache.xpath.internal.operations.Or;
import jmu.vo.Orderdetail;
import jmu.vo.Orders;
import jmu.vo.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper

public interface UserMapper {

    /*买家登陆功能*/
    @Select ("select * from userinfo where user_id=#{user_id} and user_pwd=#{user_pwd}")
    public Userinfo  login(String user_id,String user_pwd);

         /*买家注册功能*/
    @Insert("insert into userinfo  values(#{user_id},#{user_nickname},#{user_email},#{user_tel},#{user_avatar},#{user_pwd})")
    public   boolean enroll(Userinfo userinfo);

    /*买家查看订单*/
    @Select("select * from  orders where user_id=#{user_id}")
    public List<Orders> searchOrders(String  user_id);

    /*买家查看订单详情*/
    @Select("select * from orderdetail where  order_id=#{order_id}")
    public  List<Orderdetail>  searchOrderdetail(String order_id);

    /*买家生成订单*/
    /*步骤 :  1.勾选商品  2.点击确认生成订单  3.可以旺订单中添加商品*/
    /*订单其他信息在用户支付后商家下单后才更新*/
         @Insert("insert into  orders(order_id,user_id,create_time,order_status,order_totalprice) values(#{order_id},#{user_id},#{create_time},#{order_status},#{order_totalprice})")
    public    boolean addOrder(Orders orders);



         /*按照商品id查找相应的商品价格*/
    @Select("select item_price, item_discount from orderitem where item_id=#{item_id}")
     public Map<String,Object> getItemPrice(@Param("item_id") String item_id);


    /*买家在订单界面添加订单详情(购买商品)*/


    /*买家支付*/


}

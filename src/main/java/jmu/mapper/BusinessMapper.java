package jmu.mapper;


import jmu.vo.Business;
import jmu.vo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BusinessMapper {
    /*卖家登陆*/
    @Select("select * from business where business_id=#{business_id} and business_pwd=#{business_pwd}")
    public Business login(String business_id,String business_pwd);


    /*买家查看所有用户下的订单*/
    @Select(" select * from orders where order_id in(select order_id from orderdetail where item_id in(select item_id from orderitem where" +
            " business_id=#{business_id}))")
    public List<Orders> showOrdersByBusiness_id(String business_id);
}

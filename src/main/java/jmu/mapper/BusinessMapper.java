package jmu.mapper;


import jmu.vo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessMapper {
    /*卖家登陆*/
    @Select("select * from business where business_id=#{business_id} and business_pwd=#{business_pwd}")
    public Business login(String business_id,String business_pwd);


    /*买家查看所有用户下的订单*/
    @Select(" select * from orders where order_id in(select order_id from orderdetail where item_id in(select item_id from orderitem where" +
            " business_id=#{business_id}))")
    public List<Orders> showOrdersByBusiness_id(String business_id);

    /*商家查看已发货的订单*/
    @Select(" select * from orders where order_id in(select order_id from orderdetail where item_id in(select item_id from orderitem where" +
            " business_id=#{business_id}))  and shipment_status='已发货'")
    public List<Orders> showShippedOrdersByBusiness_id(String business_id);


    /*商家查看未发货的订单*/
    @Select(" select * from orders where order_id in(select order_id from orderdetail where item_id in(select item_id from orderitem where" +
            " business_id=#{business_id}))  and shipment_status='未发货'")
    public List<Orders> showUnshippedOrdersByBusiness_id(String business_id);

    /*买家发货step1:修改订单发货状态为'已发货'*/
    @Update("update orders set shipment_status='已发货' where order_id=#{order_id}")
    public  int  alterShipmentStatus(String order_id);

    /*根据订单查找到用户id*/
    @Select("select user_id  from orders where order_id=#{order_id}")
    public  String  findUseridByOrderid(String order_id);

    /*根据订单用户id查找到用户详细地址*/
    @Select("select detail_address from address where user_id=#{user_id}")
    public List<String> showDetailAddressByUserid(String user_id);

    /*商家查找所有物流公司*/
    @Select("select  * from couriercompanies  ")
    public List<Couriercompanies> listAllCompanies();

    /*商家生成快递单*/
    @Insert("insert into shipment values(#{order_id},#{business_id},#{company_name},#{ship_address},#{ship_date},#{waybill_id})")
    public  boolean AddShipment(Shipment shipment);



    /*商家生成运单*/
    @Insert("insert into  waybill values(#{waybill_id},#{company_id},#{waybill_status})")
    public boolean   AddWaybill(Waybill waybill);

    /*根据快递公司名称查找相应的公司id*/
    @Select("select  company_id from couriercompanies where company_name=#{company_name}")
    public String findCompanyidByname(String company_id);

    /*商家新增商品*/

    @Insert("insert into orderitem values(#{item_id},#{business_id},#{category_id},#{item_name},#{item_url},#{item_profile},#{item_price},#{item_discount},#{inventory})")
    public   boolean addOrderitem(Orderitem orderitem);

    /*商家查看自己店铺所对应得商品*/
    @Select("select * from orderitem where business_id=#{business_id}")
    @Results({
            @Result(id = true,property = "item_id",column = "item_id"),
            @Result(property = "itemCateGory",column = "category_id",
                    javaType = ItemCateGory.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchCategoryById",fetchType = FetchType.LAZY)
            )
    })
    public   List<Orderitem>  showOrderitemByBusinessid(String business_id);


    /*根据每个商家对应的商品生成消费报表*/
    /*String中为商品id,Object表示商品销售数额*/
    @Select("SELECT oi.item_name as item_name, sum(od.pay_price) as total_sales " +
            "FROM orderdetail od " +
            "INNER JOIN orders o on od.order_id = o.order_id " +
            "INNER JOIN orderitem oi on od.item_id = oi.item_id " +
            "INNER JOIN business b on oi.business_id = b.business_id " +
            "WHERE b.business_id = #{business_id} " +
            "GROUP BY oi.item_name")
    public List<Map<String,Object>> getBusinessTotalSales(@Param("business_id")String business_id);

    @Update("update orderitem set item_url=#{item_url} where item_id=#{item_id}")
    public int updateUrl(String item_url,String item_id);










}

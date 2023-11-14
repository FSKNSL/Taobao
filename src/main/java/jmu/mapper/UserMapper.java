package jmu.mapper;

import jmu.vo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper

public interface UserMapper {

    /*买家登陆功能*/
    @Select ("select * from userinfo where user_id=#{user_id} and user_pwd=#{user_pwd}")
    public Userinfo login(String user_id,String user_pwd);

         /*买家注册功能*/
    @Insert("insert into userinfo  values(#{user_id},#{user_nickname},#{user_email},#{user_tel},#{user_avatar},#{user_pwd})")
    public   boolean enroll(Userinfo userinfo);

    /*用户查看个人信息*/
    @Select("select * from  userinfo where user_id=#{user_id}")
    public  Userinfo showUserInfo(String user_id);


    /*用户查看收货地址*/
    @Select("select * from address where user_id=#{user_id}")
    @Results({
            @Result(id = true,property = "address_id",column = "address_id"),
            @Result(property = "district",column = "district_id",
                    javaType = District.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchDisByDid",fetchType = FetchType.LAZY))
    })
    public  List<Address> showAddress(String user_id);

    /*用户编辑收货地址*/
    @Update("update address set district_id=#{district_id},receipt_name=#{receipt_name},receipt_tel=#{receipt_tel},detail_address=#{detail_address},receipt_email=#{receipt_email} where address_id=#{address_id}")
    public  int  alterAddress(String district_id,String receipt_name,String receipt_tel,String detail_address,String receipt_email,String address_id);

    /*传入省信息*/

    @Select("select * from province")
    public List<Province>listAllProvince();
    @Select("select * from province where province_id=#{province_id}")
    public Province searchProByPid(String province_id);


    /*传入市信息*/
    @Select("select * from  city")
    public List<City>listAllCity();
    @Select("select * from city where city_id=#{city_id}")
    @Results({
            @Result(id = true,property = "city_id",column = "city_id"),
            @Result(property = "province",column = "province_id",
                    javaType = Province.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchProByPid",fetchType = FetchType.LAZY))
    })
    public City searchCityByCid(String city_id);

    /*传入区信息*/
    @Select("select * from district")
    public  List<District>listAllDistrict();
    @Select("select * from district where district_id=#{district_id}")
    @Results({
            @Result(id = true,property = "district_id",column = "district_id"),
            @Result(property = "city",column = "city_id",
                    javaType = City.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchCityByCid",fetchType = FetchType.LAZY))
    })
    public District searchDisByDid(String district_id);

    /*用户新建收货地址*/
    @Insert("insert  into address(district_id,user_id,receipt_name,receipt_tel,detail_address,receipt_email)values(#{district_id},#{user_id},#{receipt_name},#{receipt_tel},#{detail_address},#{receipt_email})")
    public   boolean addAddress(Address address);



    @Update("update userinfo set user_nickname=#{user_nickname},user_email=#{user_email},user_tel=#{user_tel},user_pwd=#{user_pwd}where user_id=#{user_id}")
    public   int  alterUserInfo(String user_id,String user_nickname,String user_email,String user_tel,String user_pwd);
    /*买家查看订单*/
    @Select("select * from  orders where user_id=#{user_id}")
    @Results({
            @Result(id = true,property = "order_id",column = "order_id"),
            @Result(property = "orderdetailList",column = "order_id",
                    javaType = List.class,
                    many = @Many(select = "jmu.mapper.UserMapper.searchOrderdetail",
                            fetchType = FetchType.LAZY)),
            @Result(property = "appraise",column = "order_id",
                    javaType = Appraise.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchAppraiseByOid",fetchType = FetchType.LAZY))
    })
    public List<Orders> searchOrders(String  user_id);

    @Select("select * from appraise where order_id=#{order_id}")
    public Appraise searchAppraiseByOid(String order_id);
    /*买家查看订单详情*/
    @Select("select * from orderdetail where  order_id=#{order_id}")
    @Results({
            @Result(id = true,property = "orderdetail_id",column = "orderdetail_id"),
            @Result(property = "orderitem",column = "item_id",
                    javaType = Orderitem.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchItemByid",fetchType = FetchType.LAZY))
    })
    public  List<Orderdetail>  searchOrderdetail(String order_id);

/*根据订单详情的商品id显示所有商品*/
    @Select("select * from orderitem where item_id=#{item_id}")
    @Results({
            @Result(id = true,property = "item_id",column = "item_id"),
            @Result(property = "itemCateGory",column = "category_id",
                    javaType = ItemCateGory.class,
                    one = @One(select = "jmu.mapper.UserMapper.searchCategoryById",fetchType = FetchType.LAZY)
            )
    })
    public Orderitem searchItemByid(String item_id);

    @Select("select * from itemcategory where category_id=#{category_id}")
    public ItemCateGory searchCategoryById(String category_id);
    /*买家生成订单*/
    /*步骤 :  1.勾选商品  2.点击确认生成订单  3.可以旺订单中添加商品*/
    /*订单其他信息在用户支付后商家下单后才更新*/
         @Insert("insert into  orders(order_id,user_id,create_time,order_status,order_totalprice,shipment_status) values(#{order_id},#{user_id},#{create_time},#{order_status},#{order_totalprice},#{shipment_status})")
    public    boolean addOrder(Orders orders);



         /*按照商品id查找相应的商品价格*/
    @Select("select item_price, item_discount from orderitem where item_id=#{item_id}")
     public Map<String,Object> getItemPrice(@Param("item_id") String item_id);


    /*买家由订单自动生成订单详情订单详情(购买商品)*/
    @Insert("insert into  orderdetail(item_id,order_id,item_number,pay_price,total_discount)values(#{item_id},#{order_id},#{item_number},#{pay_price},#{total_discount})")
    public   boolean addOrderdetail(Orderdetail orderdetail);



    /*买家支付,虚拟支付,点击相应的order完成虚拟支付*/
    @Update("update  orders set order_status='已支付' where order_id=#{order_id}")
    public   int  payOrders(String orders_id);


    /*用户字符订单后生成的支付记录,随着支付而生成，不可撤销*/
    @Insert("insert into pay values(#{order_id},#{user_id},#{pay_date},#{pay_method},#{pay_number})")
            public boolean  payOrders2(Pay pay);

    /*买家对订单进行评价*/
    @Insert("insert  into appraise values(#{user_id},#{order_id},#{appraise_time},#{appraise_grade},#{appraise_content}) ")
    public  boolean appraiseOrders(Appraise appraise);


    /*展示所有商品的信息,在douserLogin里面自动显示*/
    @Select("select * from orderitem")
    public  List<Orderitem>listAllItems();


    /*用户查看当前购物车*/
    @Select("select * from shoppingcart where user_id=#{user_id}")
    public   List<Shoppingcart>listAllCart(String user_id);


    /*用户点击商品下方添加至购物车操作*/
    @Insert("insert into shoppingcart (user_id,item_id,item_url,item_price,item_number,item_name) values(#{user_id},#{item_id},#{item_url},#{item_price},#{item_number},#{item_name})")
    public boolean addShoppingCart(Shoppingcart shoppingcart);



    /*按照cart_id修改购物车商品数量和价格,价格随数量同时改动*/
    @Update("update shoppingcart  set  item_number=#{item_number} where cart_id=#{cart_id}")
    public  int  alterShoppingCart(int item_number,int cart_id);


    /*按照cart_id删除购物车记录*/
    @Delete("delete   from shoppingcart where cart_id=#{cart_id}")
    public int deleteShoppingcart(int cart_id);


    /*用户查看自己的支付记录*/
    @Select("select * from  pay where user_id=#{user_id}")
    public   List<Pay> listAllPay(String user_id);

    @Update("update userinfo set user_avatar=#{user_avatar} where user_id=#{user_id}")
    public int updateAvatar(String user_id,String user_avatar);

    /*根据shoppingcart的id查找商品id*/
    @Select("select item_id from shoppingcart where cart_id=#{cart_id}")
    public String   selectItemidByCartId(int  cart_id);


    /*根据shoppingcart的id查找商品数量*/
    @Select("select  item_number from shoppingcart where cart_id=#{cart_id}")
    public   int   selectItemnumberByCartId(int cart_id);

    /*商品的模糊查询*/
    @Select("SELECT * FROM orderitem WHERE item_name LIKE CONCAT('%', #{keyword}, '%')")
    public List<Orderitem> selectOrderitemByKeyword(String keyword);

    /*购物车的删除,批量下单后自动删除购物车*/
    @Delete("delete from  shoppingcart where  cart_id=#{cart_id}")
    public  boolean   deleteshoppingcartByid(int  cart_id);

    /*生成某卖家在各个商店的消费报表*/

    @Select("SELECT b.business_id as business_id, sum( od.pay_price) as total_sales " +
            "FROM orders o " +
            "INNER JOIN orderdetail od on od.order_id = o.order_id " +
            "INNER JOIN orderitem oi on oi.item_id = od.item_id " +
            "INNER JOIN business b on b.business_id = oi.business_id " +
            "WHERE o.user_id = #{user_id} " +
            "GROUP BY b.business_id")
    public  List<Map<String, Object>> getUserTotalSales(@Param("user_id") String user_id);
    @Update("update orderdetail set receipt_status=#{receipt_status} where orderdetail_id=#{orderdetail_id}")
    public int updateReceiptStatus(int orderdetail_id,String receipt_status);
}

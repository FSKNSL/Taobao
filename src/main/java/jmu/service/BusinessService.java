package jmu.service;


import jmu.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface BusinessService {
    public Business login(String business_id,String business_pwd);

    public List<Orders> showOrdersByBusiness_id(String business_id);

    public List<Orders> showShippedOrdersByBusiness_id(String business_id);

    public List<Orders> showUnshippedOrdersByBusiness_id(String business_id);

    public  int  alterShipmentStatus(String order_id);

    public List<String> showDetailAddressByUserid(String user_id);

    public  String  findUseridByOrderid(String order_id);

    public List<Couriercompanies> listAllCompanies();

    public  boolean AddShipment(Shipment shipment);

    public boolean   AddWaybill(Waybill waybill);
    public String findCompanyidByname(String company_id);

    public   boolean addOrderitem(Orderitem orderitem);

    public   List<Orderitem>  showOrderitemByBusinessid(String business_id);

    public List<Map<String,Object>> getBusinessTotalSales(@Param("business_id")String business_id);

}

package jmu.service;


import jmu.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}

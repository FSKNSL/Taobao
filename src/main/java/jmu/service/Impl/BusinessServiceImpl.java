package jmu.service.Impl;

import jmu.mapper.BusinessMapper;
import jmu.service.BusinessService;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;
    @Override
    public Business login(String business_id, String business_pwd) {
        return businessMapper.login(business_id,business_pwd);
    }

    @Override
    public List<Orders> showOrdersByBusiness_id(String business_id) {
        return businessMapper.showOrdersByBusiness_id(business_id);
    }

    @Override
    public List<Orders> showShippedOrdersByBusiness_id(String business_id) {
        return businessMapper.showShippedOrdersByBusiness_id(business_id);
    }

    @Override
    public List<Orders> showUnshippedOrdersByBusiness_id(String business_id) {
        return businessMapper.showUnshippedOrdersByBusiness_id(business_id);
    }

    @Override
    public int alterShipmentStatus(String order_id) {
        return businessMapper.alterShipmentStatus(order_id);
    }

    @Override
    public List<String> showDetailAddressByUserid(String user_id) {
        return  businessMapper.showDetailAddressByUserid(user_id);
    }

    @Override
    public String findUseridByOrderid(String order_id) {
        return  businessMapper.findUseridByOrderid(order_id);
    }

    @Override
    public List<Couriercompanies> listAllCompanies() {
        return businessMapper.listAllCompanies();
    }

    @Override
    public boolean AddShipment(Shipment shipment) {
        businessMapper.AddShipment(shipment);
        return  true;
    }

    @Override
    public boolean AddWaybill(Waybill waybill) {
        businessMapper.AddWaybill(waybill);
        return true;
    }

    @Override
    public String findCompanyidByname(String company_id) {
        return businessMapper.findCompanyidByname(company_id);
    }

    @Override
    public boolean addOrderitem(Orderitem orderitem) {
        businessMapper.addOrderitem(orderitem);
        return true;
    }

    @Override
    public List<Orderitem> showOrderitemByBusinessid(String business_id) {
        return businessMapper.showOrderitemByBusinessid(business_id);
    }

    @Override
    public List<Map<String, Object>> getBusinessTotalSales(String business_id) {
        return businessMapper.getBusinessTotalSales(business_id);
    }

    @Override
    public int updateUrl(String item_url, String item_id) {
        return businessMapper.updateUrl(item_url,item_id);
    }
}

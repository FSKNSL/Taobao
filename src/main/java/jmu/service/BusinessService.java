package jmu.service;


import jmu.vo.Business;
import jmu.vo.Orders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BusinessService {
    public Business login(String business_id,String business_pwd);

    public List<Orders> showOrdersByBusiness_id(String business_id);
}

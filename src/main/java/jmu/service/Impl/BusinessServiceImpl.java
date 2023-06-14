package jmu.service.Impl;

import jmu.mapper.BusinessMapper;
import jmu.service.BusinessService;
import jmu.vo.Business;
import jmu.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

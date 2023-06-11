package jmu.service.Impl;

import jmu.mapper.BusinessMapper;
import jmu.service.BusinessService;
import jmu.vo.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;
    @Override
    public Business login(String business_id, String business_pwd) {
        return businessMapper.login(business_id,business_pwd);
    }
}

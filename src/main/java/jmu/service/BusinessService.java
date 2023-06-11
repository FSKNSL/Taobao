package jmu.service;


import jmu.vo.Business;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BusinessService {
    public Business login(String business_id,String business_pwd);
}

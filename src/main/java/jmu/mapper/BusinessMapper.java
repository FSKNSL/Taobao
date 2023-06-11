package jmu.mapper;


import jmu.vo.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BusinessMapper {
    /*卖家登陆*/
    @Select("select * from business where business_id=#{business_id} and business_pwd=#{business_pwd}")
    public Business login(String business_id,String business_pwd);
}

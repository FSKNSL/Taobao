package jmu.mapper;

import jmu.vo.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper

public interface UserMapper {

    /*买家登陆功能*/
    @Select ("select * from userinfo where user_id=#{user_id} and user_pwd=#{user_pwd}")
    public Userinfo  login(String user_id,String user_pwd);

         /*买家注册功能*/
    @Insert("insert into userinfo  values(#{user_id},#{user_nickname},#{user_email},#{user_tel},#{user_avatar},#{user_pwd})")
    public   boolean enroll(Userinfo userinfo);


}

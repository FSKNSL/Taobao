package jmu.vo;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Userinfo {
    private String user_id;
    private String user_nickname;
    private String user_email;
    private String user_tel;
    private String user_avatar;
    private String user_pwd;
}

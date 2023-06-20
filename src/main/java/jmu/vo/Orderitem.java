package jmu.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class Orderitem {
    private  String    item_id;
    private     String   business_id;
    private     String category_id;
    private   String  item_name;
    private    String    item_url;
    private   String   item_profile;
    private BigDecimal item_price;
    private   BigDecimal  item_discount;
    private    int  inventory;
    private ItemCateGory itemCateGory;
}

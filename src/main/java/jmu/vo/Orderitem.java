package jmu.vo;

import lombok.Data;

@Data
public class Orderitem {
    private  String    item_id;
    private     String   business_id;
    private     String category_id;
    private   String  item_name;
    private    String    item_url;
    private   String   item_profile;
    private   float   item_price;
    private   float  item_discount;
    private    int  inventory;
}

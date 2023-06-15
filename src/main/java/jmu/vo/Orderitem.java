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
    private   double   item_price;
    private   double  item_discount;
    private    int  inventory;
}

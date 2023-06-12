package jmu.vo;

import lombok.Data;

@Data
public class Orderdetail {

    private String  orderdetail_id;
    private  String item_id;
    private  String order_id;
    private   int item_number;
    private    float pay_price;
    private    float total_discount;

}

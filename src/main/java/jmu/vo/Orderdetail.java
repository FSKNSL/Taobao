package jmu.vo;

import lombok.Data;

@Data
public class Orderdetail {

    private String  orderdetail_id;
    private  String item_id;
    private  String order_id;
    private   int item_number;
    private    double  pay_price;
    private    double total_discount;

}

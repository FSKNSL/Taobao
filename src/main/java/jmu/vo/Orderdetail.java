package jmu.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Orderdetail {

    private String  orderdetail_id;
    private  String item_id;
    private  String order_id;
    private   int item_number;
    private BigDecimal pay_price;
    private BigDecimal total_discount;
    private   String receipt_status;
    private Orderitem orderitem;
}

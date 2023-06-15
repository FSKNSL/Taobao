package jmu.vo;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Shoppingcart {

    private   String  cart_id;
    private  String user_id;
    private  String item_id;
    private   String   item_url;
    private BigDecimal item_price;
    private   int   item_number;
    private   String item_name;

}

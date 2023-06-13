package jmu.vo;


import lombok.Data;

@Data
public class Shoppingcart {

    private   String  cart_id;
    private  String user_id;
    private  String item_id;
    private   String   item_url;
    private   float item_price;
    private   int   item_number;
    private   String item_name;

}

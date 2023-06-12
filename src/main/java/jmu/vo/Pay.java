package jmu.vo;

import lombok.Data;

@Data
public class Pay {
    private   String  order_id;
    private  String user_id;
    private  String pay_date;
    private   String pay_method;
    private    String   pay_number;

}

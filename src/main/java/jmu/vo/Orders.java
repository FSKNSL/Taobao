package jmu.vo;

import lombok.Data;

@Data
public class Orders {
    private    String order_id;
    private  String user_id;
    private  String waybill_id;
    private  String  create_time;
    private   String  order_status;
    private   float order_totalprice;
}
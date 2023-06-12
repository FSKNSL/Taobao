package jmu.vo;


import lombok.Data;

@Data
public class Shipment {
    private  String order_id;
    private  String business_id;
    private String company_name;
    private  String ship_address;
    private  String ship_date;
    private   String waybill_id;
}

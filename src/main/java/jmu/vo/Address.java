package jmu.vo;

import lombok.Data;

@Data
public class Address {
    private  String address_id;
    private String  district_id;
    private   String user_id;
    private String receipt_name;
    private  String receipt_tel;
    private String detail_address;
    private String receipt_email;
    private District district;
}

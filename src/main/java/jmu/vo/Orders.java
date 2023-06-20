package jmu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Orders {
    private    String order_id;
    private  String user_id;
    private  String waybill_id;
    private  String  create_time;
    private   String  order_status;
    private BigDecimal order_totalprice;
    private   String receipt_status;
    private   String shipment_status;
    private List<Orderdetail> orderdetailList;
}

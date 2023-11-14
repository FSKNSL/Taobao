package jmu.vo;

import lombok.Data;

@Data
public class District {
    private  String district_id;
    private String city_id;
    private String district_name;
    private City city;
}

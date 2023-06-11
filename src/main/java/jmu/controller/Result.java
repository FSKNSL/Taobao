package jmu.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private  Integer code;
    private  Object data;
    private  String msg;
    public  Result(Integer code,Object data )
    {
         this.code=code;
         this.data=data;

    }

}

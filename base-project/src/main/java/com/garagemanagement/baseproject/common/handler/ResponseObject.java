package com.garagemanagement.baseproject.common.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String status;
    private Object data;

    public static ResponseObject successResponseWithData(Object data){
        return new ResponseObject("Success!", data);
    }
}

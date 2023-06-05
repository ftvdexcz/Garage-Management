package com.garagemanagement.accessoryservice.common.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String status;
    private int statusCode;
    private String message;

    public static ResponseError responseError(AppError error){
        return new ResponseError(error.getStatus(), error.getStatusCode(), error.getMessage());
    }
}

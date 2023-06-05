package com.garagemanagement.carrepairservice.common.handler;


import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppError extends RuntimeException{
    private String status;
    private int statusCode;

    public AppError() {
    }

    public AppError(String status, int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.status = status;
    }

    public static AppError NotFoundError(String cls) {
        return new AppError("Not found!", StatusCodeResponse.STATUS_RESPONSE_NOT_FOUND, String.format("Not found %s", cls));
    }

    public static AppError ExistedNameError(String cls, String username) {
        return new AppError("Existed!", StatusCodeResponse.STATUS_RESPONSE_BAD_REQUEST, String.format("Existed %s, Username: %s", cls, username));
    }

    public static AppError UnauthorizedError(){
        return new AppError("Failed!", StatusCodeResponse.STATUS_RESPONSE_UNAUTHORIZED, "Unauthorized!");
    }

    public static AppError ForbiddenError(){
        return new AppError("Failed!", StatusCodeResponse.STATUS_RESPONSE_FORBIDDEN, "Forbidden!");
    }

    public static AppError LoginError(){
        return new AppError("Failed!", StatusCodeResponse.STATUS_RESPONSE_FORBIDDEN, "Bad creadentials!");
    }

    public static AppError ForeignKeyInvalid(String cls){
        return new AppError("Failed!", StatusCodeResponse.STATUS_RESPONSE_BAD_REQUEST, String.format("Invalid foreign key id for table %s", cls));
    }

    public static AppError BadRequestError(String msg){
        return new AppError("Failed!", StatusCodeResponse.STATUS_RESPONSE_BAD_REQUEST, msg);
    }
}

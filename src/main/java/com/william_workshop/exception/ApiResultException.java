package com.william_workshop.exception;

import com.william_workshop.components.IErrorCode;
import lombok.Getter;

@Getter
public class ApiResultException extends RuntimeException {

    private Integer code;

    public ApiResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiResultException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ApiResultException(String message) {
        super(message);
    }


}

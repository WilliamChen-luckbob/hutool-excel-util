package com.william_workshop.exception;

import com.william_workshop.components.web.ResultEnum;
import lombok.Getter;

@Getter
public class ExcelProcessingException extends RuntimeException {
    private Integer code;

    /**
     * 抛出excel异常
     *
     * @param message
     */
    public ExcelProcessingException(String message) {
        super(message);
        this.code = ResultEnum.UNKNOWN_ERROR.getCode();
    }
}

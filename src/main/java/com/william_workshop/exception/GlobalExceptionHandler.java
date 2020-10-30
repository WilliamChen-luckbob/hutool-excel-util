package com.william_workshop.exception;

import com.william_workshop.components.web.CommonResult;
import com.william_workshop.components.web.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        log.error((e.getMessage()));
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ApiResultException.class)
    public CommonResult handle2(ApiResultException e) {
        log.error((e.getMessage()));
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ExcelProcessingException.class)
    public CommonResult handleExcelException(ExcelProcessingException e) {
        log.error((e.getMessage()));
        return CommonResult.error(e.getCode(), e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e) {
        log.error(e.getMessage());
        return CommonResult.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
    }

}

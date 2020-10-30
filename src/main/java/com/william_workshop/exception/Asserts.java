package com.william_workshop.exception;


import com.william_workshop.components.web.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by macro on 2020/2/27.
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void fail(IErrorCode errorCode,String message) {
        throw new ApiException(errorCode.getCode(),message);
    }

    public static void fail(int code,String message) {
        throw new ApiException(code,message);
    }

}

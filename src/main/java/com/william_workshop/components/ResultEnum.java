package com.william_workshop.components;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-06-11 18:56
 */
@Getter
public enum ResultEnum implements IErrorCode {

    UNKNOWN_ERROR(-1, "未知错误"),

    SUCCESS(0, "成功"),

    PARAM_ERROR(1,"参数不正确"),

    UPDATE_ERROR(7,"更新数据失败"),

    EMPTY_PRODUCTS(101, "商品信息为空"),

    PRODUCT_NOT_EXIST(10, "不存在的商品"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(26, "登出成功"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

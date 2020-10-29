package com.william_workshop.controllers;

import com.william_workshop.components.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "excel工具类测试demo")
@RestController
@RequestMapping("/excel")
public class Demo {
    @ApiOperation(value = "测试swagger正常")
    @RequestMapping(method = RequestMethod.POST, value = "/connect")
    public CommonResult testConn() {
        return CommonResult.success("连接正常");
    }
}

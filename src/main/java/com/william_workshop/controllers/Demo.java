package com.william_workshop.controllers;

import com.google.common.collect.Lists;
import com.william_workshop.components.excel.ExcelPageBase;
import com.william_workshop.components.web.CommonResult;
import com.william_workshop.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.log.LogInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

package com.william_workshop.controllers;

import com.google.common.collect.Lists;
import com.william_workshop.components.excel.SheetDTO;
import com.william_workshop.components.web.CommonResult;
import com.william_workshop.entities.CustomizedExcelReadTestForm;
import com.william_workshop.entities.SimplifiedExcelReadTestForm;
import com.william_workshop.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "excel工具类测试demo")
@RestController
@RequestMapping("/excel")
public class Demo {
    /**
     * 测试swagger连接正常
     *
     * @return
     */
    @ApiOperation(value = "测试swagger正常")
    @RequestMapping(method = RequestMethod.POST, value = "/connect")
    public CommonResult testConn() {
        return CommonResult.success("连接正常");
    }

    /**
     * 简单读取excel，excel字段名必须和实体一一对应
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "简单读取excel")
    @RequestMapping(method = RequestMethod.POST, value = "/simple/read")
    public CommonResult readFile(@RequestPart(name = "file") MultipartFile file) {
        //第一个参数为excel文件，第二个为接收数据的实体类，第三个为读取excel的第几个sheet
        List<SimplifiedExcelReadTestForm> excelInfo = ExcelUtils.getExcelInfo(file, SimplifiedExcelReadTestForm.class, 0);
        return CommonResult.success(excelInfo);
    }

    /**
     * 简单读取excel，如果报错，将会返回具体的报错数据，excel字段名必须和实体一一对应
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "简单读取excel并校验是否有重复")
    @RequestMapping(method = RequestMethod.POST, value = "/simple/read/check")
    public CommonResult readFileAndCheck(@RequestPart(name = "file") MultipartFile file) {
        //第一个参数为excel文件，第二个为接收数据的实体类，第三个为读取excel的第几个sheet
        List<SimplifiedExcelReadTestForm> excelInfo = ExcelUtils.getExcelInfoThenCheck(file, SimplifiedExcelReadTestForm.class, 0);
        return CommonResult.success(excelInfo);
    }

    /**
     * 将实体直接导出为excel
     *
     * @param response
     */
    @ApiOperation(value = "简单导出excel,带数据")
    @RequestMapping(method = RequestMethod.POST, value = "/simple/export/data")
    public void exportSimpFileWithData(HttpServletResponse response) {
        List<SimplifiedExcelReadTestForm> excelInfo = new ArrayList();
        for (int i = 0; i < 10; i++) {
            SimplifiedExcelReadTestForm info = new SimplifiedExcelReadTestForm(i, "奇奇怪怪的名字" + i, "奇奇怪怪的数据" + i);
            excelInfo.add(info);
        }
        ExcelUtils.exportSinglePageExcelWithData("奇奇怪怪的文件名", excelInfo, response);
    }

    /**
     * 直接返回一个空表
     *
     * @param response
     */
    @ApiOperation(value = "简单导出excel,不带数据")
    @RequestMapping(method = RequestMethod.POST, value = "/simple/export/nodata")
    public void exportSimpFileWithoutData(HttpServletResponse response) {
        ExcelUtils.exportSinglePageExcelWithoutData("奇奇怪怪的文件名", SimplifiedExcelReadTestForm.class, response);
    }

    /**
     * 综合应用：根据定制的实体，从excel中抓出想要的数据，并输出成指定的excel样式
     *
     * @param file
     * @param response
     */
    @ApiOperation(value = "上传客制化的excel并将处理好的excel返回")
    @RequestMapping(method = RequestMethod.POST, value = "/process/immediately")
    public void readThenOutPut(@RequestPart(name = "file") MultipartFile file, HttpServletResponse response) {
        //预定义从第几行开始读取数据（这里也可由请求参数传入起始行）
        CustomizedExcelReadTestForm parent = new CustomizedExcelReadTestForm(3);
        List<CustomizedExcelReadTestForm> readResult = ExcelUtils.getCustomizedExcelInfo(file, parent, 0);

        //抓出含有包包和帽子的关键字的数据
        List<CustomizedExcelReadTestForm> hats = new ArrayList<>();
        List<CustomizedExcelReadTestForm> bags = readResult.stream().filter(e -> {
            if (e.getGoodName().contains("帽")) {
                hats.add(e);
            }
            return e.getGoodName().contains("包");
        }).collect(Collectors.toList());

        //分别输出,其中第二页sheet不带收货人字段
        SheetDTO<CustomizedExcelReadTestForm> sheet1 = new SheetDTO<>("帽子", CustomizedExcelReadTestForm.class, hats, new ArrayList<>());
        SheetDTO<CustomizedExcelReadTestForm> sheet2 = new SheetDTO<>("包包", CustomizedExcelReadTestForm.class, bags, Lists.newArrayList("consignee"));
        ExcelUtils.exportCustomizedMultiPageExcel(Lists.newArrayList(sheet1, sheet2), "测试导出文档", response);
    }

}

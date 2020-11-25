# 基于Hutool的excel工具类

纯自学，从一个会玩玩excel的业务转行做C#，再转java，一点一点自学摸起来的，整理代码+重构还是挺锻炼人的，工具类也还没完善，但是最近在找工作吧，还是得要发一些能见人的东西出来，目前也在自己搭传说中的高并发服务集群，搭框架倒是简单，难的是像博客大佬一样，用md给图文并茂的写出来，所以代码可能不规范，请大佬们务必毫不留情的指出。



## 项目结构：

### annotations：工具类必须的注解

### components：工具类必须的通用类和组件

### config：工具类测试时使用的配置（swagger）

### controller：工具类测试使用的控制器和相应的逻辑

### exception：异常捕捉处理

### utils：工具类的核心处理类

## 使用方法：

### 1. 引入必要依赖

~~~xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.4.6</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.1.2</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
<dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.6</version>
</dependency>
~~~

### 2. 复制相关类

将annotation、components.excel、utils三个包下的内容放入项目中即可，复制过去后有一些包名引用会出问题，这里每次我给新项目加入excel工具的时候都要去改改包名，我暂时没想到有什么好办法解决，兴许打个jar包直接用maven引入项目会更好呢？

另外实体类的制定有一些规则，这里可以参考entities包下的两个类，分别是简单实体和客制化实体。

## 具体使用：

### 1. 简单的导入导出

核心问题

如果输出中文文件名，要这样进行一下header的修改，并且要重新对中文进行编码，这里swagger不能导出，可选用postman测试。

~~~java
fileName = URLEncoder.encode(fileName,"UTF-8") + ".xls";
response.setContentType("application/vnd.ms-excel;charset=utf-8");
response.setHeader("Content-Disposition","attachment;filename*=utf-8''"+fileName);
~~~



**注意：简单的导入导出唯一要注意的就是实体的字段与excel的字段要保持一致，并且第一行为标题（即字段）**

~~~java
package com.william_workshop.entities;

import com.william_workshop.annotations.NotDuplicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor//如果使用注解添加了其他构造函数，记得此处一定要有空参构造函数供反射调用
public class SimplifiedExcelReadTestForm {
    @NotDuplicate
    private Integer id;

    private String name;

    private String data;
}

~~~

### 4. 客制化excel的导入导出



### 3. 目前支持的功能的示例代码

~~~java
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
~~~








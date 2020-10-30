# 基于Hutool的excel工具类

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





~~~java
/**
     * 简单读取excel，excel字段名必须和实体一一对应
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
     * @param file
     * @return
     */
@ApiOperation(value = "简单读取excel并校验是否有重复")
@RequestMapping(method = RequestMethod.POST, value = "/simple/read")
public CommonResult readFileAndCheck(@RequestPart(name = "file") MultipartFile file) {
    //第一个参数为excel文件，第二个为接收数据的实体类，第三个为读取excel的第几个sheet
    List<SimplifiedExcelReadTestForm> excelInfo = ExcelUtils.getExcelInfoThenCheck(file, SimplifiedExcelReadTestForm.class, 0);
    return CommonResult.success(excelInfo);
}
~~~








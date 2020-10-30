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

![image-20201029221000306](../资源/image-20201029221000306.png)

将annotation、components.excel、utils三个包下的内容放入项目中即可，复制过去后有一些包名引用会出问题，这里每次我给新项目加入excel工具的时候都要去改改包名，我暂时没想到有什么好办法解决，兴许打个jar包直接用maven引入项目会更好呢？




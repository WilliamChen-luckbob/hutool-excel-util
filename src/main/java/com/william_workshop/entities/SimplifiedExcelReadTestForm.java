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

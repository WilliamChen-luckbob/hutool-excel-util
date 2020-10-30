package com.william_workshop.entities;

import com.william_workshop.annotations.NotDuplicate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimplifiedExcelReadTestForm {
    @NotDuplicate
    private Integer id;

    private String name;

    private String data;
}

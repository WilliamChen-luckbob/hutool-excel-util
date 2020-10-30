package com.william_workshop.entities;

import com.william_workshop.annotations.ColProperties;
import com.william_workshop.components.excel.ExcelPageBase;
import lombok.Data;

/**
 * 先达香港-客户模板抓取定义
 */
@Data
public class CustomizedExcelReadTestForm extends ExcelPageBase {
    public CustomizedExcelReadTestForm(Integer startRow) {
        this.setStartRow(startRow);
    }

    public CustomizedExcelReadTestForm() {
        this.setStartRow(3);
    }

    @ColProperties(name = "单号", col = 4)
    private String trackingNo;

    @ColProperties(name = "商品重量", col = 7)
    private String weightPerPiece;

    @ColProperties(name = "商品名称", col = 9)
    private String goodName;

    @ColProperties(name = "商品备注", col = 8)
    private String content;

    @ColProperties(name = "商品数量", col = 10)
    private String pcs;

    @ColProperties(name = "收货人", col = 14)
    private String consignee;

    @ColProperties(name = "收货地址", col = 15)
    private String consigneeAddress;

    @ColProperties(name = "收货人联系方式", col = 16)
    private String consigneeTel;
}

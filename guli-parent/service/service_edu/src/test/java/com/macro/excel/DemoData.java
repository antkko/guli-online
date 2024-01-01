package com.macro.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/11/30 18:22
 */
@Data
public class DemoData
{
	@ExcelProperty(value = "学生编号", index = 0)
	private Integer sno;

	@ExcelProperty(value = "学生姓名", index = 1)
	private String snmae;
}

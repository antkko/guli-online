package com.macro.serviceedu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/11/30 18:50
 */
@Data
public class SubjectData
{
	@ExcelProperty(index = 0)
	private String oneSubjectName;

	@ExcelProperty(index = 1)
	private String twoSubjectName;
}

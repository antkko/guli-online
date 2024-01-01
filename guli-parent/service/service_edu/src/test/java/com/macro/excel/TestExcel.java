package com.macro.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther macro
 * @description
 * @date 2023/11/30 18:24
 */
public class TestExcel
{
	public static void main(final String[] args)
	{
		// 1设置写入文件夹地址和excel文件名称
		// final String fileName = "";

		// 2调用easyexcel里面方法实现些操作
		// EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(TestExcel.getData());

		// easyexcel读操作
		final String fileName = "";
		EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
	}

	public static List<DemoData> getData()
	{
		final ArrayList<DemoData> dataList = new ArrayList<>();
		final DemoData data = new DemoData();
		for (int i = 0; i < 15; i++) {
			data.setSno(i);
			data.setSnmae("lucy" + i);
			dataList.add(data);
		}
		return dataList;
	}
}

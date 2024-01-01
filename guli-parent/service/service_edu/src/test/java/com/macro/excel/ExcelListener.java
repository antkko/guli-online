package com.macro.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @auther macro
 * @description
 * @date 2023/11/30 18:31
 */
public class ExcelListener extends AnalysisEventListener<DemoData>
{
	/**
	 * 一行一行读取excel数据
	 *
	 * @param data
	 * @param context
	 */
	@Override
	public void invoke(final DemoData data, final AnalysisContext context)
	{
		System.out.println("***" + data);
	}

	/**
	 * 读取表头的方法
	 *
	 * @param headMap
	 * @param context
	 */
	@Override
	public void invokeHeadMap(final Map<Integer, String> headMap, final AnalysisContext context)
	{
		System.out.println("表头" + headMap);
	}

	/**
	 * 读取完成之后
	 *
	 * @param context
	 */
	@Override
	public void doAfterAllAnalysed(final AnalysisContext context)
	{

	}
}

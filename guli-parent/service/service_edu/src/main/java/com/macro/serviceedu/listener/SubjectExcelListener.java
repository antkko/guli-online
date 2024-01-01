package com.macro.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.macro.servicebase.exception.GuliException;
import com.macro.serviceedu.entity.EduSubject;
import com.macro.serviceedu.entity.excel.SubjectData;
import com.macro.serviceedu.service.EduSubjectService;

/**
 * @auther macro
 * @description
 * @date 2023/11/30 18:54
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData>
{
	// 因为SubjectExcelListener不能交给spring进行管理,需要自己new,不能注入其他对象
	// 不能实现数据库操作

	private EduSubjectService subjectService;

	public SubjectExcelListener() {}

	public SubjectExcelListener(final EduSubjectService subjectService)
	{
		this.subjectService = subjectService;
	}

	@Override
	public void invoke(final SubjectData data, final AnalysisContext context)
	{
		if (data == null) {
			throw new GuliException(20001, "文件数据为空");
		}

		// 判断一级分类是否为空
		EduSubject existsOneSubject = this.existsOneSubject(this.subjectService, data.getOneSubjectName());
		// 没有重复一级分类,进行添加
		if (existsOneSubject == null) {
			existsOneSubject = new EduSubject();
			existsOneSubject.setParentId("0");
			existsOneSubject.setTitle(data.getOneSubjectName());
			this.subjectService.save(existsOneSubject);
		}

		final String pid = existsOneSubject.getId(); // 获取一级分类的id
		// 添加二级分类
		EduSubject existsTwoSubject = this.existsTwoSubject(this.subjectService, data.getTwoSubjectName(), pid);
		if (existsTwoSubject == null) {
			existsTwoSubject = new EduSubject();
			existsTwoSubject.setParentId(pid);
			existsTwoSubject.setTitle(data.getTwoSubjectName());
			this.subjectService.save(existsTwoSubject);
		}
	}

	/**
	 * 判断一级分类不能重复
	 *
	 * @param subjectService
	 * @param name
	 * @return
	 */
	private EduSubject existsOneSubject(final EduSubjectService subjectService, final String name)
	{
		final QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title", name).eq("parent_id", "0");
		return subjectService.getOne(queryWrapper);
	}

	/**
	 * 判断二级分类不能重复
	 *
	 * @param subjectService
	 * @param name
	 * @return
	 */
	private EduSubject existsTwoSubject(final EduSubjectService subjectService, final String name, final String pid)
	{
		final QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title", name).eq("parent_id", pid);
		return subjectService.getOne(queryWrapper);
	}

	@Override
	public void doAfterAllAnalysed(final AnalysisContext context)
	{

	}
}

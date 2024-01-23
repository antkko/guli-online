package com.macro.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.servicebase.exception.GuliException;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.EduCourseDescription;
import com.macro.serviceedu.entity.frontvo.CourseFrontVo;
import com.macro.serviceedu.entity.frontvo.CourseWebVo;
import com.macro.serviceedu.entity.vo.CourseInfoVO;
import com.macro.serviceedu.entity.vo.CoursePublishVo;
import com.macro.serviceedu.mapper.EduCourseMapper;
import com.macro.serviceedu.service.EduChapterService;
import com.macro.serviceedu.service.EduCourseDescriptionService;
import com.macro.serviceedu.service.EduCourseService;
import com.macro.serviceedu.service.EduVideoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 课程 服务实现类
 *
 * @author macro
 * @since 2023-12-05
 */
@Service
@RequiredArgsConstructor
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
        implements EduCourseService {

    private final EduCourseDescriptionService courseDescriptionService;

    private final EduVideoService videoService;

    private final EduChapterService chapterService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(final CourseInfoVO courseInfoVO) {
        // 1.向课程表添加课程基本信息
        final EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        final int result = baseMapper.insert(eduCourse);
        final String courseId = eduCourse.getId();

        if (result <= 0) {
            throw new GuliException(20001, "添加课程基本信息失败");
        }
        // 2.向课程简介表添加课程简介信息
        final EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        // 描述的id就是课程的id
        eduCourseDescription.setId(courseId);
        courseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseInfoVO getCourseInfo(final String courseId) {
        // 1.查询课程表
        final EduCourse eduCourse = baseMapper.selectById(courseId);
        final CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVO);

        // 2.查询描述表
        final EduCourseDescription eduCourseDescription =
                courseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(eduCourseDescription.getDescription());

        return courseInfoVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCourse(final String courseId) {
        // 1.根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        // 2. 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        // 3.根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        // 4.根据课程id删除课程课程本身
        final int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除课程失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(final Page<EduCourse> pageParam,
                                                  final CourseFrontVo courseInfoVO) {
        // 1.根据条件查询课程
        final QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断条件值是否为空，不为空拼接条件
        // 一级分类
        if (StringUtils.isNotEmpty(courseInfoVO.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseInfoVO.getSubjectParentId());
        }
        // 二级分类
        if (StringUtils.isNotEmpty(courseInfoVO.getSubjectId())) {
            wrapper.eq("subject_id", courseInfoVO.getSubjectId());
        }
        // 关注度
        if (StringUtils.isNotEmpty(courseInfoVO.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        // 最新
        if (StringUtils.isNotEmpty(courseInfoVO.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        // 价格
        if (StringUtils.isNotEmpty(courseInfoVO.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        final List<EduCourse> records = pageParam.getRecords();
        final Long current = pageParam.getCurrent();
        final Long pages = pageParam.getPages();
        final Long size = pageParam.getSize();
        final Long total = pageParam.getTotal();
        final boolean hasNext = pageParam.hasNext(); // 下一页
        final boolean hasPrevious = pageParam.hasPrevious(); // 上一页

        // 把分页数据获取出来，封装到map集合里面
        final Map<String, Object> map = new HashMap<>(7);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", Optional.of(hasNext));
        map.put("hasPrevious", Optional.of(hasPrevious));

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(final String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public CoursePublishVo publishCourseInfo(final String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseInfo(final CourseInfoVO courseInfoVO) {
        // 1.修改课程表
        final EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);

        final int result = baseMapper.updateById(eduCourse);
        if (result == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        // 2.修改描述表
        final EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVO.getId());
        courseDescription.setDescription(courseDescription.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }
}

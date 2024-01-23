package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduSubject;
import com.macro.serviceedu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目 服务类
 *
 * @author macro
 * @since 2023-11-30
 */
public interface EduSubjectService extends IService<EduSubject> {


    /**
     * 添加课程分类
     *
     * @param file           上传的文件
     * @param subjectService 课程分类服务类
     */
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    /**
     * 查询课程列表(树形展示)
     *
     * @return 课程列表
     */
    List<OneSubject> getOneTwoSubject();
}

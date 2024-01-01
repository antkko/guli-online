package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduSubject;
import com.macro.serviceedu.entity.subject.OneSubject;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
   * @param file 上传的文件
   */
  void saveSubject(MultipartFile file, EduSubjectService subjectService);

  /**
   * 查询课程列表(树形展示)
   *
   * @return
   */
  List<OneSubject> getOneTwoSubject();
}

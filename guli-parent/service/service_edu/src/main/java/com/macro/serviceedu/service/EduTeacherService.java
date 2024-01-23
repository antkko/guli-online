package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author macro
 * @since 2023-11-21
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师
     *
     * @param pageTeacher 分页对象
     * @return 讲师列表
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

}

package com.macro.eduorder.client;

import com.macro.commonutils.vo.EduCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author macro
 * @description
 * @date 2024/1/10 18:29
 * @github https://github.com/bugstackss
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程id获取课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    EduCourseVo getCourseInfoOrder(@PathVariable("courseId") final String courseId);
}

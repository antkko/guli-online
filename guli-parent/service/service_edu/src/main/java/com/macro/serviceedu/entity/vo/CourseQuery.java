package com.macro.serviceedu.entity.vo;

import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/19 18:48
 */
@Data
public class CourseQuery {

    /** 课程标题 */
    private String title;

    /** 课程状态 */
    private String status;
}

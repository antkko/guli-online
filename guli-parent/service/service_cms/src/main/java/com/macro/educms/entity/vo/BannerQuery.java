package com.macro.educms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author macro
 * @description 首页banner查询对象
 * @date 2023/12/29 13:30
 * @github https://github.com/bugstackss
 */
@Data
public class BannerQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 幻灯片名字 */
    private String name;

    /* 注意，这里使用的是String类型，前端传过来的数据无需进行类型转换 */
    private String begin;

    /**
     * 结束时间
     */
    private String end;
}


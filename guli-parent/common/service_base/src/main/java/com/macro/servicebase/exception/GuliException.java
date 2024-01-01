package com.macro.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @auther macro
 * @description 自定义异常类
 * @date 2023/11/22 19:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException
{
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;
}

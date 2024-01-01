package com.macro.servicebase.exception;

import com.macro.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther macro
 * @description
 * @date 2023/11/22 18:52
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(final Exception e)
    {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(final ArithmeticException e)
    {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException特定异常处理...");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(final GuliException e)
    {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}

package com.macro.serviceedu.entity.subject;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/05 18:06
 */
@Api("二级分类")
@Data
public class TwoSubject {

  private String id;

  private String title;
}

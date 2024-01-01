package com.macro.serviceedu.entity.subject;

import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/05 18:05
 */
@Api("一级分类")
@Data
public class OneSubject {

  private String id;

  private String title;

  /** 二级分类集合,一个一级中存在多个二级 */
  private List<TwoSubject> children = new ArrayList<>();
}

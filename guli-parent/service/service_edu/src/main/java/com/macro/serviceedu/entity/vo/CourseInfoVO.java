package com.macro.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/05 21:32
 */
@Data
@ApiModel(value = "课程基本信息", description = "编辑课程基本信息的表单对象")
public class CourseInfoVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "课程ID")
  private String id;

  @ApiModelProperty(value = "课程讲师ID")
  private String teacherId;

  @ApiModelProperty(value = "课程专业ID")
  private String subjectId;

  @ApiModelProperty(value = "一级分类ID")
  private String subjectParentId;

  @ApiModelProperty(value = "课程标题")
  private String title;

  @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
  private BigDecimal price;

  @ApiModelProperty(value = "总课时")
  private Integer lessonNum;

  @ApiModelProperty(value = "课程封面图片路径")
  private String cover;

  @ApiModelProperty(value = "课程简介")
  private String description;
}

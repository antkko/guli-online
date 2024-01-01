package com.macro.serviceedu.entity.chapter;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/06 20:43
 */
@Data
public class ChapterVo {

  private String id;

  private String title;

  /** 表示小节 */
  private List<VideoVo> children = new ArrayList<>();
}

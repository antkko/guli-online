package com.macro.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.serviceedu.entity.EduTeacher;
import com.macro.serviceedu.mapper.EduTeacherMapper;
import com.macro.serviceedu.service.EduTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author macro
 * @since 2023-11-21
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
        implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(final Page<EduTeacher> pageParam) {
        final QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 把分页数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageParam, wrapper);

        final List<EduTeacher> records = pageParam.getRecords();
        final long current = pageParam.getCurrent();
        final long pages = pageParam.getPages();
        final long size = pageParam.getSize();
        final long total = pageParam.getTotal();
        final boolean hasNext = pageParam.hasNext(); // 下一页
        final boolean hasPrevious = pageParam.hasPrevious(); // 上一页

        // 把分页数据获取出来，封装到map集合里面
        final Map<String, Object> map = new HashMap<>(7);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}

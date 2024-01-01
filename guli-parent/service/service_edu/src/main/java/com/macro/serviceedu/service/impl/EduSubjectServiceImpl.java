package com.macro.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.serviceedu.entity.EduSubject;
import com.macro.serviceedu.entity.excel.SubjectData;
import com.macro.serviceedu.entity.subject.OneSubject;
import com.macro.serviceedu.entity.subject.TwoSubject;
import com.macro.serviceedu.listener.SubjectExcelListener;
import com.macro.serviceedu.mapper.EduSubjectMapper;
import com.macro.serviceedu.service.EduSubjectService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目 服务实现类
 *
 * @author macro
 * @since 2023-11-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject>
        implements EduSubjectService {
    @Override
    public void saveSubject(final MultipartFile file, final EduSubjectService subjectService) {
        try {
            final InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService))
                    .sheet()
                    .doRead();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getOneTwoSubject() {

        // 1.查询一级分类 parent_id=0
        final QueryWrapper<EduSubject> queryWrapperOne = new QueryWrapper<>();
        queryWrapperOne.eq("parent_id", "0");
        final List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapperOne);

        // 2.查询二级分类 parent_id!=0
        final QueryWrapper<EduSubject> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id", "0");
        final List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapperTwo);

        // 存储最终封装的数据
        final List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3.封装一级分类
        // 查询出来所有的一级分类list集合遍历,得到每个一级分类对象,获取每个一级分类对象值
        // 封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 得到oneSubjectList中每个eduSubject
            final EduSubject eduSubject = oneSubjectList.get(i);

            // 把多个eduSubject里面的值取出来,放到OneSubject里面去
            final OneSubject oneSubject = new OneSubject();

            /* oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle()); */

            BeanUtils.copyProperties(eduSubject, oneSubject);
            // 多个OneSubject放到finalSubjectList

            finalSubjectList.add(oneSubject);

            // 在一级分类中遍历查询所有的二级分类
            // 创建list集合封装每个一级分类中的二级数据
            final List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            // 获取每个二级分类
            for (final EduSubject twoSubjectSource : twoSubjectList) {
                // 判断二级分类的parent_id与一级分类的id是否一致
                if (twoSubjectSource.getParentId().equals(eduSubject.getId())) {
                    final TwoSubject twoSubjectTarget = new TwoSubject();
                    BeanUtils.copyProperties(twoSubjectSource, twoSubjectTarget);

                    twoFinalSubjectList.add(twoSubjectTarget);
                }
            }
            // 把一级分类下的所有二级分类放到一级分类中去
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}

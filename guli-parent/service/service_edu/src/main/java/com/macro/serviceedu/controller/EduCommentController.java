package com.macro.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.JwtUtils;
import com.macro.commonutils.R;
import com.macro.commonutils.vo.UcenterMemberVo;
import com.macro.servicebase.exception.GuliException;
import com.macro.serviceedu.client.UcenterClient;
import com.macro.serviceedu.entity.EduComment;
import com.macro.serviceedu.service.EduCommentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
@RestController
@RequestMapping("/serviceedu/edu-comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    // 根据课程id_分页查询课程评论的方法
    @GetMapping("/getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable final Long page, @PathVariable final Long limit, final String courseId) {
        final Page<EduComment> commentPage = new Page<>(page, limit);
        final QueryWrapper<EduComment> wrapper = new QueryWrapper<>();

        // 判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)) {
            wrapper.eq("course_id", courseId);
        }

        // 按最新排序
        wrapper.orderByDesc("gmt_create");

        // 数据会被封装到commentPage中
        eduCommentService.page(commentPage, wrapper);

        final List<EduComment> commentList = commentPage.getRecords();
        final long current = commentPage.getCurrent();// 当前页
        final long size = commentPage.getSize();// 一页记录数
        final long total = commentPage.getTotal();// 总记录数
        final long pages = commentPage.getPages();// 总页数
        final boolean hasPrevious = commentPage.hasPrevious();// 是否有上页
        final boolean hasNext = commentPage.hasNext();// 是否有下页

        final HashMap<String, Object> map = new HashMap<>(10);
        map.put("current", current);
        map.put("size", size);
        map.put("total", total);
        map.put("pages", pages);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        map.put("list", commentList);

        return R.ok().data(map);
    }

    // 添加评论
    @PostMapping("/auth/addComment")
    public R addComment(final HttpServletRequest request, @RequestBody final EduComment eduComment) {
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 判断用户是否登录
        if (StringUtils.isEmpty(memberId)) {
            throw new GuliException(20001, "请先登录");
        }
        eduComment.setMemberId(memberId);

        // 远程调用ucenter根据用户id获取用户信息
        final UcenterMemberVo memberVo = ucenterClient.getMemberInfoById(memberId);

        eduComment.setAvatar(memberVo.getAvatar());
        eduComment.setNickname(memberVo.getNickname());

        // 保存评论
        eduCommentService.save(eduComment);

        return R.ok();
    }

}



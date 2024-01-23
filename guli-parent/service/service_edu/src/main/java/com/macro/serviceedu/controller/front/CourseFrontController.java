package com.macro.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.JwtUtils;
import com.macro.commonutils.R;
import com.macro.commonutils.vo.EduCourseVo;
import com.macro.serviceedu.client.OrdersClient;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.chapter.ChapterVo;
import com.macro.serviceedu.entity.frontvo.CourseFrontVo;
import com.macro.serviceedu.entity.frontvo.CourseWebVo;
import com.macro.serviceedu.service.EduChapterService;
import com.macro.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2024/1/2 21:03
 * @github https://github.com/bugstackss
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Resource
    private EduCourseService eduCourseService;

    @Resource
    private EduChapterService eduChapterService;

    @Resource
    private OrdersClient ordersClient;

    /**
     * 条件查询带分页查询课程
     *
     * @param page  当前页
     * @param limit 每页记录数
     * @return 查询结果
     */
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable final long page,
                                @PathVariable final long limit,
                                @RequestBody(required = false) final CourseFrontVo courseFrontVo) {
        final Page<EduCourse> pageCourse = new Page<>(page, limit);
        final Map<String, Object> map = eduCourseService.getCourseFrontList(pageCourse, courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 课程详情的方法
     *
     * @param courseId 课程ID
     * @return 查询结果
     */
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable final String courseId, final HttpServletRequest request) {
        // 根据课程id，编写sql语句查询课程信息
        final CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        // 根据课程id查询章节和小节
        final List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);

        // 根据课程id和用户id查询当前课程是否已经支付过了
        final boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));


        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy", buyCourse);
    }

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return 查询结果
     */
    @PostMapping("/getCourseInfoOrder/{courseId}")
    public EduCourseVo getCourseInfoOrder(@PathVariable final String courseId) {
        final CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(courseId);
        final EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(courseInfo, eduCourseVo);
        return eduCourseVo;
    }
}

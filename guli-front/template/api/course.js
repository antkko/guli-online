import request from "@/utils/request";

export default {
  //前台多条件分页查询
  getConditionPage(page, limit, searchObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: "post",
      data: searchObj,
    });
  },
  //查询所有分类（一级分类、二级分类）的方法
  getAllSubject() {
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: "get",
    });
  },
  // 课程详情的方法
  getCourseInfo(id) {
    return request({
      url: `/eduservice/coursefrontgetFrontCourseInfo/${id}`,
      method: "get",
    });
  },
};

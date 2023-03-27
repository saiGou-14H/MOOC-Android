package com.org.moocapp.api;

public class ApiConfig {
    public static final int PAGE_SIZE = 4;
//    public static final String BASE_URL = "http://www.saigoumvp.work:9001";
    public static final String BASE_URL = "http://192.168.31.26:9001";

    public static final String appStudent = "/app/student/";
    public static final String shCourse = "/app/course/shCourse/";



    public static final String shCourseByType = "/app/course/shCourseByType/";
    public static final String shCourseByParentType = "/app/course/shCourseByParentType/";
    public static final String shCourseLike = "/app/course/shCourseLike/";
    public static final String shType= "/app/course/type/shTypeById/";
    public static final String shChirlType= "/app/course/type/shChileTypeByPraId/";
    public static final String shMaxType= "/app/course/type/shMaxType/";

    public static final String shStudentCourse= "/app/student/shStudentCourse/";
    public static final String shHaveCourse= "/app/student/shHaveCourse/";

    public static final String adCourseComment= "/app/student/comment/";
    public static final String shCourseChapter= "/app/chapter/shChapterByCourseId/";

    public static final String shCourseComment= "/app/course/shCourseComment/";
    public static final String shClass= "/App/student/shClass";

    public static final String shPracticeByCouserId= "/app/practice/shPracticeByCouserId/";
    public static final String shPracticeByStudentId= "/app/student/shPractice/";
    public static final String adPractice= "/app/student/adPractice";
    public static final String LOGIN = "/user/login"; //登录

    public static final String shUser = "/app/user/shUser"; //查询个人信息

    public static final String Sign = "/app/user/sign"; //查询个人信息


    public static final String shLearnProgress = "/app/study/shLearnProgress/";
    public static final String upLearnProgress = "/app/study/upLearnProgress/";

    public static final String adCourseCar = "/app/car/adCourse/";
    public static final String shIntegralHistory = "/app/integralhistory/shintegralhistory";




    public static final String Message_LIST_ALL = "/find/mMessage/searchMMessageByPage";//查询所有资讯列表
    public static final String MessageCommentBy_LIST_ALL = "/find/mMessageComment/searchMMessageCommentBy";//资讯的所有评论列表
    public static final String MessageComment_ADD = "/find/mMessageComment/add";//发表资讯的评论

    public static final String PROBLEM_LIST_ALL = "/find/mQuestionAnswer/searchMQuestionAnswerByPage";//查询所有问题列表
    public static final String PROBLEM_CommentBy_LIST_ALL = "/find/mQuestionComment/searchMQuestionCommentBy";//问题的所有评论列表
    public static final String PROBLEM_ADD = "/find/mQuestionAnswer/add";//发表问题
    public static final String PROBLEM_GET_BY_Id = "/find/mQuestionAnswer/searchMQuestionAnswerById";//根据问题Id查询问题详情
    public static final String PROBLEM_IS_MY = "/find/mQuestionAnswer/searchMQuestionAnswerByIdAndUserId";//根据问题Id查询是否是User的问题
    public static final String PROBLEM_Comment_ADD = "/find/mQuestionComment/add";//发表问题的评论
    public static final String PROBLEM_Comment_TAKE = "/find/mQuestionComment/takeQuestionComment";//发表问题的评论

    public static final String CART_LIST_ALL = "/find/mStudentCourseCart/searchStudentCourseCart";//分页查询用户的购物车
    public static final String CART_DELETE = "/find/mStudentCourseCart/delete";//删除购物车

    public static final String COURSE_LIST = "/find/mStudentCourseOrder/searchCourseList";//查询提交订单课程接口
    public static final String MY_ORDER_COURSE_LIST = "/find/mStudentCourseOrder/searchOrderCourseByPage";//查询我的订单课程接口
    public static final String ORDER_ADD = "/find/mStudentCourseOrder/addOrder";//下单

    public static final String GET_USERINFO = "/find/mUser/getUserinfo";//获取个人信息
    public static final String UPDATE_USERINFO = "/find/mUser/update";//修改个人信息
    public static final String UPDATE_HEAD = "/find/mUser/uploadHead";//修改个人头像

    public static final String SEARCH_MY_QUESTION_BY_PAGE = "/find/mQuestionAnswer/searchMyMQuestionAnswerByPage";//分页查询我的问题列表
    public static final String SEARCH_MY_QUESTION_COMMENT_BY_PAGE = "/find/mQuestionComment/searchMyMQuestionCommentByPage";//分页查询我的回答（评论）列表

}


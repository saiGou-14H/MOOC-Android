package com.org.moocapp.api;

public class ApiConfig {
    public static final int PAGE_SIZE = 5;
    public static final String BASE_URl = "http://192.168.0.165:8089";
    public static final String LOGIN = "/mUser/login"; //登录
    public static final String REGISTER = "/mUser/register";//注册


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

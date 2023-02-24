package com.org.moocapp.api1;

import com.org.moocapp.R;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.entity_mysql.MCourseChapter;
import com.org.moocapp.entity_mysql.MCourseType;
import com.org.moocapp.entity_mysql.MStudentCourse;

import java.util.ArrayList;

public class api {
    public static ArrayList<MCourse> getCourseList(){
        ArrayList<MCourse> courseinfos = new ArrayList<>();
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        courseinfos.add(new MCourse(R.mipmap.course,"课程名称",1,"课程描述"));
        return courseinfos;
    }

    public static ArrayList<MCourse> getImageInfos(){
        ArrayList<MCourse> imageInfos = new ArrayList<>();
        imageInfos.add(new MCourse(R.mipmap.lunbo1,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo2,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo3,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo4,"课程名称",1,"课程描述"));
        return imageInfos;
    }

    public static ArrayList<MCourseType> getCourseTypeInfos(){
        ArrayList<MCourseType> mCourseTypes = new ArrayList<>();
        MCourseType type1 = new MCourseType();
        type1.setType("康复护理");
        mCourseTypes.add(type1);
        MCourseType type2 = new MCourseType();
        type2.setType("法律法规");
        mCourseTypes.add(type2);
        MCourseType type3 = new MCourseType();
        type3.setType("生活常识");
        mCourseTypes.add(type3);
        MCourseType type4 = new MCourseType();
        type4.setType("更多");
        mCourseTypes.add(type4);
        return mCourseTypes;
    }

    public static ArrayList<MCourseChapter> getCourseChapterInfos(){
        ArrayList<MCourseChapter> mcoursechapter = new ArrayList<>();
        mcoursechapter.add(new MCourseChapter(1,"枚举"));

        mcoursechapter.add(new MCourseChapter(2,"递归"));

        mcoursechapter.add(new MCourseChapter(3,"动态规划"));

        mcoursechapter.add(new MCourseChapter(4,"八数码问题"));

        mcoursechapter.add(new MCourseChapter(5,"迷宫问题"));

        mcoursechapter.add(new MCourseChapter(6,"八贪心算法"));

        return mcoursechapter;
    }

    public static  ArrayList<MStudentCourse> getStudentCourseInfos(){
        ArrayList<MStudentCourse> StudentCourse = new ArrayList<>();
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        return StudentCourse;
    }

}

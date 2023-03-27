package com.org.moocapp.entity.Response;
import com.org.moocapp.entity.ClassEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@NoArgsConstructor
@Data
public class ClassResponse implements Serializable {

    private int code;
    private String message;
    private List<ClassListEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ClassListEntity> getData() {
        return data;
    }

    public void setData(List<ClassListEntity> data) {
        this.data = data;
    }

    @NoArgsConstructor
    @Data
    public  class ClassListEntity implements Serializable{
        private long stuId;

        public long getStuId() {
            return stuId;
        }

        public void setStuId(long stuId) {
            this.stuId = stuId;
        }

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public ClassEntity getClassX() {
            return classX;
        }

        public void setClassX(ClassEntity classX) {
            this.classX = classX;
        }

        private int classId;
        private String joinDate;
        private ClassEntity classX;


    }
}

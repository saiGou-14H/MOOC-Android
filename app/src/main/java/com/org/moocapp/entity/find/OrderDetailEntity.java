package com.org.moocapp.entity.find;

import java.io.Serializable;

public class OrderDetailEntity implements Serializable {
    /**
     * 雪花ID
     */
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 课程价格
     */
    private Integer price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

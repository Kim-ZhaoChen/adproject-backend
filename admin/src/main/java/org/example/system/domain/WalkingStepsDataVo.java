package org.example.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 步数历史
 * 对象 walking_steps_history
 *
 * @author louis
 * @date 2024-08-10
 */
public class WalkingStepsDataVo {
    private static final long serialVersionUID = 1L;



    private Long steps;
    private Long distance;
    private Long calories;
    private Long targetSteps;
    private Long progress;

    private int order;

    private Long userId;

    private String userName;

    private String avatar;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }


    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getTargetSteps() {
        return targetSteps;
    }

    public void setTargetSteps(Long targetSteps) {
        this.targetSteps = targetSteps;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }


}

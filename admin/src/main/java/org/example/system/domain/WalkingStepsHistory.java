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
public class WalkingStepsHistory {
    private static final long serialVersionUID = 1L;


    private String id;
    private Long steps;
    private Long userId;
    private Long distance;
    private Long calories;
    private Long targetSteps;
    private Long progress;
    private Long targetFinished;
    private Date startTime;
    private Long endime;
    private Long activateMinutes;
    private Long delFlag;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getTargetFinished() {
        return targetFinished;
    }

    public void setTargetFinished(Long targetinished) {
        this.targetFinished = targetinished;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getEndime() {
        return endime;
    }

    public void setEndime(Long endime) {
        this.endime = endime;
    }

    public Long getActivateMinutes() {
        return activateMinutes;
    }

    public void setActivateMinutes(Long activateMinutes) {
        this.activateMinutes = activateMinutes;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

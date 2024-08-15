package org.example.system.service.impl;


import org.example.common.core.utils.DateUtils;
import org.example.common.core.utils.StringUtils;
import org.example.common.core.web.domain.AjaxResult;
import org.example.system.domain.*;
import org.example.system.mapper.WalkingStepsHistoryMapper;
import org.example.system.security.utils.SecurityUtils;
import org.example.system.service.IWalkingStepsHistoryService;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 步数历史
 * Service业务层处理
 *
 * @author louis
 * @date 2024-08-10
 */
@Service
public class WalkingStepsHistoryServiceImpl implements IWalkingStepsHistoryService {
    @Autowired
    private WalkingStepsHistoryMapper walkingStepsHistoryMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询步数历史
     *
     * @param id 步数历史
     *           主键
     * @return 步数历史
     */
    @Override
    public WalkingStepsHistory selectWalkingStepsHistoryById(String id) {
        return walkingStepsHistoryMapper.selectWalkingStepsHistoryById(id);
    }

    /**
     * 查询步数历史
     * 列表
     *
     * @param walkingStepsHistory 步数历史
     * @return 步数历史
     */
    @Override
    public List<WalkingStepsVo> selectWalkingStepsHistoryList(WalkingStepsVo walkingStepsHistory) {
        return walkingStepsHistoryMapper.selectWalkingStepsHistoryList(walkingStepsHistory);
    }

    /**
     * 新增步数历史
     *
     * @param walkingStepsHistory 步数历史
     * @return 结果
     */
    @Override
    public AjaxResult insertWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory) {
        walkingStepsHistory.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser = sysUserService.selectUserById(walkingStepsHistory.getUserId());
        if (sysUser == null) {
            return AjaxResult.error("您输入的用户不存在请检查");
        }
        WalkingStepsHistory stepsHistory = new WalkingStepsHistory();
        stepsHistory.setCreateTime(DateUtils.getNowDate());
        stepsHistory.setSteps(Long.valueOf(walkingStepsHistory.getSteps()));
        stepsHistory.setId(UUID.randomUUID().toString().substring(0, 15));
        stepsHistory.setCreateBy(SecurityUtils.getUsername());
        stepsHistory.setCreateTime(new Date());
        stepsHistory.setUserId(walkingStepsHistory.getUserId());


//        int steps, int sex, double height, double weight, int activateMinutes
        double[] doubles = DailyActivityCalculator.calculateDailyDistanceCalories(walkingStepsHistory.getSteps().intValue(), sysUser.getSex(), sysUser.getHeight().doubleValue(), sysUser.getWeight().doubleValue(), walkingStepsHistory.getActivateMinutes().intValue());

        stepsHistory.setTargetSteps(getGoalByUser(walkingStepsHistory.getUserId().toString()));
        stepsHistory.setCalories((long) Math.round(doubles[1]));
        stepsHistory.setActivateMinutes(walkingStepsHistory.getActivateMinutes());
        stepsHistory.setDistance(Math.round(doubles[0]));
        if (walkingStepsHistory.getSteps() >= stepsHistory.getTargetSteps()) {
            walkingStepsHistory.setProgress(Long.valueOf(100));
            walkingStepsHistory.setTargetFinished(Long.valueOf(1));

        } else {
            double result = (double) walkingStepsHistory.getSteps() / stepsHistory.getTargetSteps();
            // 使用 DecimalFormat 格式化结果
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedResult = df.format(result);
            System.out.println("Formatted Result: " + formattedResult);
            // 使用 BigDecimal 格式化结果
            BigDecimal bd = new BigDecimal(result);
            bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
            walkingStepsHistory.setTargetFinished(Long.valueOf(0));
            walkingStepsHistory.setProgress(Long.valueOf(bd.toString()));
        }
        return AjaxResult.success(walkingStepsHistoryMapper.insertWalkingStepsHistory(stepsHistory));

    }


    /**
     * 修改步数历史
     *
     * @param walkingStepsHistory 步数历史
     * @return 结果
     */
    @Override
    public int updateWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory) {
        walkingStepsHistory.setUpdateTime(DateUtils.getNowDate());
        return walkingStepsHistoryMapper.updateWalkingStepsHistory(walkingStepsHistory);
    }

    /**
     * 批量删除步数历史
     *
     * @param ids 需要删除的步数历史
     *            主键
     * @return 结果
     */
    @Override
    public int deleteWalkingStepsHistoryByIds(String[] ids) {
        return walkingStepsHistoryMapper.deleteWalkingStepsHistoryByIds(ids);
    }

    /**
     * 删除步数历史
     * 信息
     *
     * @param id 步数历史
     *           主键
     * @return 结果
     */
    @Override
    public int deleteWalkingStepsHistoryById(String id) {
        return walkingStepsHistoryMapper.deleteWalkingStepsHistoryById(id);
    }

    @Override
    public List<ByTypeStepsVo> getByType(String type, String userId) {
        return walkingStepsHistoryMapper.getByType(type, userId);
    }

    @Override
    public int goalUser(GoalUser goalUser) {
        Long goalByUser = walkingStepsHistoryMapper.getGoalByUser(goalUser.getUserId());
        if(goalByUser == null){
            goalUser.setId(UUID.randomUUID().toString().substring(0, 15));
            goalUser.setCreateTime(DateUtils.getNowDate());
            goalUser.setCreateBy(SecurityUtils.getUsername());
            walkingStepsHistoryMapper.goalUser(goalUser);
        }else {
            walkingStepsHistoryMapper.updateGoalUser(goalUser);
        }
        return 1;
    }

    @Override
    public Long getGoalByUser(String userId) {
        Long goalsSteps = walkingStepsHistoryMapper.getGoalByUser(userId);
        if(goalsSteps == null){
            goalsSteps = 5000L;
        }
        return goalsSteps;
    }

    @Override
    public List<WalkingStepsDataVo> selectWalkingStepsData(WalkingStepsDataParam walkingStepsDataParam) {
        //校验参数
        if(walkingStepsDataParam == null || StringUtils.isEmpty(walkingStepsDataParam.getUserId())){
            return null;
        }
        String dateType = walkingStepsDataParam.getDateType();
        if(StringUtils.isNotEmpty(dateType) && (dateType.equals("day") || dateType.equals("weekly")  || dateType.equals("month"))){
            if(dateType.equals("day")){
                walkingStepsDataParam.setStartTime(DateUtils.getTodayStartTime());
                walkingStepsDataParam.setEndTime(DateUtils.getTodayEndTime());
            }
            if(dateType.equals("weekly")){
                walkingStepsDataParam.setStartTime(DateUtils.getWeekStartTime());
                walkingStepsDataParam.setEndTime(DateUtils.getTodayEndTime());
            }
            if(dateType.equals("month")){
                walkingStepsDataParam.setStartTime(DateUtils.getMonthStartTime());
                walkingStepsDataParam.setEndTime(DateUtils.getTodayEndTime());
            }
        }else if(walkingStepsDataParam.getStartTime() == null){
            walkingStepsDataParam.setDateType(null);
            walkingStepsDataParam.setStartTime(DateUtils.getTodayStartTime());
        }
        else if(walkingStepsDataParam.getEndTime() == null){
            walkingStepsDataParam.setDateType(null);
            walkingStepsDataParam.setEndTime(DateUtils.getTodayEndTime());
        }
        List<WalkingStepsDataVo> walkingStepsDataVos = walkingStepsHistoryMapper.selectWalkingStepsData(walkingStepsDataParam);
        return walkingStepsDataVos;
    }

    @Override
    public int addFriend(FriendUser friendUser) {
        //判断是否已存在好友关系
        List<FriendUser> list = walkingStepsHistoryMapper.getFriendUser(friendUser.getUserId(), friendUser.getFriendUserId());
        if(!CollectionUtils.isEmpty(list)){
            return -1;
        }
        friendUser.setId(UUID.randomUUID().toString().substring(0, 15));
        walkingStepsHistoryMapper.addFriend(friendUser);
        return 1;
    }

    @Override
    public int delFriend(FriendUser friendUser) {
        walkingStepsHistoryMapper.delFriend(friendUser);
        return 1;
    }

    @Override
    public List<SysUser> getFriendList(String userId) {
        return walkingStepsHistoryMapper.getFriendList(userId);
    }

    @Override
    public int updateProfile(SysUser user) {
        sysUserService.updateUser(user);
        return 0;
    }

    @Override
    public List<WalkingStepsDataVo> getFriendStepRank(List<String> userIds) {
        return walkingStepsHistoryMapper.getFriendStepRank(userIds);
    }



}

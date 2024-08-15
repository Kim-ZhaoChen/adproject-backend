package org.example.system.service;


import org.example.common.core.web.domain.AjaxResult;
import org.example.system.domain.*;

import java.util.List;

/**
 * 步数历史
 * Service接口
 *
 * @author louis
 * @date 2024-08-10
 */
public interface IWalkingStepsHistoryService {
    /**
     * 查询步数历史
     *
     * @param id 步数历史
     *           主键
     * @return 步数历史
     */
    public WalkingStepsHistory selectWalkingStepsHistoryById(String id);

    /**
     * 查询步数历史
     * 列表
     *
     * @param walkingStepsHistory 步数历史
     * @return 步数历史
     * 集合
     */
    public List<WalkingStepsVo> selectWalkingStepsHistoryList(WalkingStepsVo walkingStepsHistory);

    /**
     * 新增步数历史
     *
     * @param walkingStepsHistory 步数历史
     * @return 结果
     */
    public AjaxResult insertWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory);

    /**
     * 修改步数历史
     *
     * @param walkingStepsHistory 步数历史
     * @return 结果
     */
    public int updateWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory);

    /**
     * 批量删除步数历史
     *
     * @param ids 需要删除的步数历史
     *            主键集合
     * @return 结果
     */
    public int deleteWalkingStepsHistoryByIds(String[] ids);

    /**
     * 删除步数历史
     * 信息
     *
     * @param id 步数历史
     *           主键
     * @return 结果
     */
    public int deleteWalkingStepsHistoryById(String id);

    List<ByTypeStepsVo> getByType(String type, String userId);

    public int goalUser(GoalUser goalUser);

    Long getGoalByUser(String userId);

    /**
     * 查看跑步数据
     * @param walkingStepsDataParam
     * @return
     */
    public List<WalkingStepsDataVo> selectWalkingStepsData(WalkingStepsDataParam walkingStepsDataParam);

    public int addFriend(FriendUser friendUser);

    public int delFriend(FriendUser friendUser);

    public List<SysUser> getFriendList(String userId);

    public int updateProfile(SysUser user);

    public List<WalkingStepsDataVo> getFriendStepRank(List<String> userIds);

}

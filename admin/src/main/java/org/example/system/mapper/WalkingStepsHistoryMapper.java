package org.example.system.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.system.domain.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 步数历史
 * Mapper接口
 *
 * @author louis
 * @date 2024-08-10
 */
public interface WalkingStepsHistoryMapper {
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
    public int insertWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory);

    /**
     * 修改步数历史
     *
     * @param walkingStepsHistory 步数历史
     * @return 结果
     */
    public int updateWalkingStepsHistory(WalkingStepsHistory walkingStepsHistory);

    /**
     * 删除步数历史
     *
     * @param id 步数历史
     *           主键
     * @return 结果
     */
    public int deleteWalkingStepsHistoryById(String id);

    /**
     * 批量删除步数历史
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWalkingStepsHistoryByIds(String[] ids);

    List<ByTypeStepsVo> getByType( @Param("type") String type, @Param("userId") String userId);

    Integer goalUser(GoalUser goalUser);

    Long getGoalByUser(String userId);

    List<WalkingStepsDataVo> selectWalkingStepsData(WalkingStepsDataParam walkingStepsDataParam);

    public List<FriendUser> getFriendUser(@Param("userId") String userId,@Param("friendUserId") String friendUserId);

    public int addFriend(FriendUser friendUser);

    public int delFriend(FriendUser friendUser);

    public List<SysUser> getFriendList(@Param("userId") String userId);

    public List<WalkingStepsDataVo> getFriendStepRank(@Param("userIds")List<String> userIds);

    public int updateGoalUser(GoalUser goalUser);

}
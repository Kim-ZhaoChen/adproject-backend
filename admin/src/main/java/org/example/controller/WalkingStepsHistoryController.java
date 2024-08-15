package org.example.controller;

import org.example.common.core.utils.DateUtils;
import org.example.common.core.utils.StringUtils;
import org.example.common.core.web.controller.BaseController;
import org.example.common.core.web.domain.AjaxResult;
import org.example.common.core.web.page.TableDataInfo;
import org.example.system.domain.*;
import org.example.system.log.annotation.Log;
import org.example.system.log.enums.BusinessType;
import org.example.system.security.annotation.RequiresPermissions;
import org.example.system.service.IWalkingStepsHistoryService;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 步数历史
 * Controller
 *
 * @author louis
 * @date 2024-08-10
 */
@RestController
@RequestMapping("/history")
public class WalkingStepsHistoryController extends BaseController {
    @Autowired
    private IWalkingStepsHistoryService walkingStepsHistoryService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询步数历史
     * 列表
     */
    @RequiresPermissions("example:history:list")
    @GetMapping("/list")
    public TableDataInfo list(WalkingStepsVo walkingStepsHistory) {
        startPage();
        List<WalkingStepsVo> list = walkingStepsHistoryService.selectWalkingStepsHistoryList(walkingStepsHistory);
        return getDataTable(list);
    }

//    /**
//     * 导出步数历史
//     * 列表
//     */
//    @org.example.system.security.annotation.RequiresPermissions("example:history:export")
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, WalkingStepsHistory walkingStepsHistory) {
//        List<WalkingStepsHistory> list = walkingStepsHistoryService.selectWalkingStepsHistoryList(walkingStepsHistory);
//        ExcelUtil<WalkingStepsHistory> util = new ExcelUtil<WalkingStepsHistory>(WalkingStepsHistory.class);
//        util.exportExcel(response, list, "步数历史数据");
//    }

    /**
     * 获取步数历史
     * 详细信息
     */
    @RequiresPermissions("example:history:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(walkingStepsHistoryService.selectWalkingStepsHistoryById(id));
    }

    /**
     * 查看跑步数据
     */
    @GetMapping(value = "/getStepData")
    public AjaxResult getStepData(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "dateType") String dateType,
                                  @RequestParam(value = "startTime") String startTime,
                                  @RequestParam(value = "endTime") String endTime) {

        WalkingStepsDataParam walkingStepsDataParam = new WalkingStepsDataParam();
        walkingStepsDataParam.setUserId(userId);
        walkingStepsDataParam.setDateType(dateType);
        if(StringUtils.isNotEmpty(startTime)){
            walkingStepsDataParam.setStartTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD,startTime));
        }
        if(StringUtils.isNotEmpty(endTime)){
            endTime = endTime + " 23:59:59";
            walkingStepsDataParam.setEndTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,endTime));
        }
        return success(walkingStepsHistoryService.selectWalkingStepsData(walkingStepsDataParam));
    }

    /**
     * 设置目标
     */
    @PostMapping(value = "/goalUser")
    public AjaxResult goalUser(@RequestBody GoalUser goalUser) {
        return success(walkingStepsHistoryService.goalUser(goalUser));
    }

    /**
     * 查看目标
     */
    @GetMapping(value = "/getGoalByUser/{userId}")
    public AjaxResult getGoalByUser(@PathVariable String userId) {
        Long goalsSteps = walkingStepsHistoryService.getGoalByUser(userId);
        WalkingStepsDataParam param = new WalkingStepsDataParam();
        param.setUserId(userId);
        param.setDateType("day");
        List<WalkingStepsDataVo> list = walkingStepsHistoryService.selectWalkingStepsData(param);
        WalkingStepsDataVo vo = new WalkingStepsDataVo();
        vo.setTargetSteps(goalsSteps);
        if(!CollectionUtils.isEmpty(list)){
            vo.setSteps(list.get(0).getSteps());
        }else {
            vo.setSteps(0L);
        }
        return success(vo);
    }

    /**
     * 获取步数历史
     * 详细信息
     */
    @GetMapping(value = "/getByType")
    public AjaxResult getByType(@RequestParam("type") String type,
                                @RequestParam("userId") String userId) {
        return success(walkingStepsHistoryService.getByType(type,userId));
    }

    /**
     * 新增步数历史
     */
    @Log(title = "步数历史", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WalkingStepsHistory walkingStepsHistory) {
        return walkingStepsHistoryService.insertWalkingStepsHistory(walkingStepsHistory);
    }

    /**
     * 修改步数历史
     */
    @RequiresPermissions("example:history:edit")
    @Log(title = "步数历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WalkingStepsHistory walkingStepsHistory) {
        return toAjax(walkingStepsHistoryService.updateWalkingStepsHistory(walkingStepsHistory));
    }

    /**
     * 删除步数历史
     */
    @RequiresPermissions("example:history:remove")
    @Log(title = "步数历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(walkingStepsHistoryService.deleteWalkingStepsHistoryByIds(ids));
    }

    /**
     * 添加好友
     */
    @PostMapping(value = "/addFriend")
    public AjaxResult addFriend(@RequestBody FriendUser friendUser) {
        int flag = walkingStepsHistoryService.addFriend(friendUser);
        if(flag == -1){
            return success("好友关系已存在！");
        }
        return success("添加成功");
    }

    /**
     * 删除好友
     */
    @PostMapping(value = "/delFriend")
    public AjaxResult delFriend(@RequestBody FriendUser friendUser) {
        walkingStepsHistoryService.delFriend(friendUser);
        return success("删除成功");
    }

    /**
     * 获取好友列表
     */
    @GetMapping(value = "/getFriends")
    public AjaxResult getFriends(@RequestParam("userId") String userId,
                                 @RequestParam(value = "userName", required = false) String userName) {
        ArrayList<SysUserVo> list = new ArrayList<>();
        List<SysUser> friendList = walkingStepsHistoryService.getFriendList(userId);
        if(!StringUtils.isEmpty(userName)) {
            List<SysUser> sysUserList = sysUserService.selectUserLikeUserName(userName);
            List<Long> userIdList = friendList.stream().map(SysUser::getUserId).collect(Collectors.toList());
            friendList.clear();
            for (SysUser sysUser : sysUserList) {
                if(!userIdList.contains(sysUser.getUserId())){
                    friendList.add(sysUser);
                }
            }
        }
        for (SysUser sysUser : friendList) {
            SysUserVo userVo = new SysUserVo();
            userVo.setUserId(sysUser.getUserId());
            userVo.setAge(sysUser.getAge());
            userVo.setHeight(sysUser.getHeight());
            userVo.setWeight(sysUser.getWeight());
            userVo.setNickName(sysUser.getNickName());
            userVo.setUserName(sysUser.getUserName());
            userVo.setEmail(sysUser.getEmail());
            userVo.setPhonenumber(sysUser.getPhonenumber());
            userVo.setSex(sysUser.getSex());
            list.add(userVo);
        }

        return success(list);
    }

    /**
     * 更新个人信息
     */
    @PostMapping(value = "/updateProfile")
    public AjaxResult updateProfile(@RequestBody SysUser sysUser) {
        return success(walkingStepsHistoryService.updateProfile(sysUser));
    }

    /**
     * 查看好友步数排行
     */
    @GetMapping(value = "/getFriendStepRank")
    public AjaxResult getFriendStepRank(@RequestParam("userId") String userId) {
        List<SysUser> friendList = walkingStepsHistoryService.getFriendList(userId);
        List<String> userIds = friendList.stream().map(sysUser -> String.valueOf(sysUser.getUserId())).collect(Collectors.toList());
        List<WalkingStepsDataVo> list = walkingStepsHistoryService.getFriendStepRank(userIds);
        for (int i = 0; i < list.size(); i++) {
            SysUser sysUser = sysUserService.selectUserById(list.get(i).getUserId());
            list.get(i).setAvatar(sysUser.getAvatar());
            list.get(i).setUserName(sysUser.getUserName());
            list.get(i).setOrder(i+1);
        }
        return success(list);
    }
}

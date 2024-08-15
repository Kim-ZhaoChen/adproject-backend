package org.example.controller;

import org.example.common.core.utils.StringUtils;
import org.example.common.core.web.controller.BaseController;
import org.example.common.core.web.domain.AjaxResult;
import org.example.common.core.web.page.TableDataInfo;
import org.example.system.domain.SysNotice;
import org.example.system.log.annotation.Log;
import org.example.system.log.enums.BusinessType;
import org.example.system.security.annotation.RequiresPermissions;
import org.example.system.security.utils.SecurityUtils;
import org.example.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Autowired
    private SysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 获取通知数量
     */
    @GetMapping("/count/{userId}")
    public AjaxResult list(@PathVariable Long userId) {
        List<SysNotice> noticeList = noticeService.selectNoticeList(new SysNotice());
        int count = 0;
        for (SysNotice sysNotice : noticeList) {
            if(StringUtils.isEmpty(sysNotice.getReceiverId())){
                count++;
                continue;
            }
            List<String> userIdList = Arrays.asList(sysNotice.getReceiverId().split(","));
            if(!userIdList.contains(String.valueOf(userId))){
                count++;
            }
        }
        return success(count);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @GetMapping(value = "/getInfo")
    public AjaxResult getInfo(@RequestParam("noticeId") Long noticeId,
                              @RequestParam("userId") String userId) {
        SysNotice sysNotice = noticeService.selectNoticeById(noticeId);
        String receiverId = sysNotice.getReceiverId();
        receiverId = StringUtils.isEmpty(receiverId) ?  userId + "," : receiverId + userId + ",";
        sysNotice.setReceiverId(receiverId);
        noticeService.updateNotice(sysNotice);
        return success(sysNotice);
    }

    /**
     * 新增通知公告
     */
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}

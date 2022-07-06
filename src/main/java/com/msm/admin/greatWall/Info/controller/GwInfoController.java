package com.msm.admin.greatWall.Info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.Info.entity.GwScenicInfo;
import com.msm.admin.greatWall.Info.service.GwInfoService;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.utils.DateUtil;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.ImageUtils;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 16:42
 */
@RestController
@RequestMapping(value = "/gwInfo")
public class GwInfoController {

    @Autowired
    private GwInfoService gwInfoService;


    @PutMapping
    public GwScenicInfo save(@RequestBody GwScenicInfo info){
        QueryWrapper<GwScenicInfo> wrapper=new QueryWrapper<>();

        //判断部门是否存在
        SysUser user = SubjectUtils.getCurrentUser();
        if (StringUtils.isEmpty(info.getDepId())){
            info.setDepId(user.getDepId());
            wrapper.eq("dep_id",user.getDepId());
        }else {
            wrapper.eq("dep_id",info.getDepId());
        }
        List<Object> list;
        wrapper.eq("title",info.getTitle());
        wrapper.eq("cate_id",info.getCateId());
        if (StringUtils.isEmpty(info.getCreateDate())){
            info.setCreateDate(DateUtil.getNowTime());
        }
        if (info.getStatus() ==null){
            info.setStatus(0);
        }

        info.setDepId(SubjectUtils.getCurrentUser().getDepId());

        if (StringUtils.isNotEmpty(info.getId())){
            wrapper.ne("id",info.getId());
             list= gwInfoService.listObjs(wrapper);
        }else {
            list=gwInfoService.listObjs(wrapper);
        }

        boolean flag = list.size() ==0;
        if (flag){
            gwInfoService.saveOrUpdate(info);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }

        return info;
    }


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        gwInfoService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public GwScenicInfo getById(@PathVariable String id){
        GwScenicInfo info = gwInfoService.getById(id);
        if (SubjectUtils.isAdminNull()){
            String content="";
            if (StringUtils.isNotEmpty(info.getContent())){
                 content = ImageUtils.getContent(info.getContent());
            }
            info.setContent(content);
        }
        return info;
    }


    @GetMapping(value = "/list")
    public IPage page(Page<GwScenicInfo> page,GwScenicInfo info){
        return gwInfoService.pageSearch(page,info);
    }

    @GetMapping(value = "/review/{id}/{status}")
    public void status(@PathVariable String id,@PathVariable Integer status){
        gwInfoService.setStatus(id,status);

    }

}

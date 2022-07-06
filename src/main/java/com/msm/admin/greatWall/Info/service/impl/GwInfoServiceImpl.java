package com.msm.admin.greatWall.Info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.Info.entity.GwScenicInfo;
import com.msm.admin.greatWall.Info.mapper.GwInfoMapper;
import com.msm.admin.greatWall.Info.service.GwInfoService;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 16:44
 */
@Service
public class GwInfoServiceImpl extends ServiceImpl<GwInfoMapper, GwScenicInfo> implements GwInfoService {


    @Autowired
    private GwInfoMapper infoMapper;

    @Autowired
    private SysDepartService departService;


    @Override
    public IPage pageSearch(Page<GwScenicInfo> page, GwScenicInfo info) {
        QueryWrapper<GwScenicInfo> infoQueryWrapper =new QueryWrapper<>();

        infoQueryWrapper= SubjectUtils.isAdmin(infoQueryWrapper);
        if (info !=null){
            if (StringUtils.isNotEmpty(info.getTitle())){
                infoQueryWrapper.like("title",info.getTitle());
            }
            if (info.getCateId() !=null){
                infoQueryWrapper.eq("cate_id",info.getCateId());
            }
            if (info.getStatus() !=null){

                if (info.getStatus() == 20){
                    infoQueryWrapper.eq("status",0).or().eq("status",2);
                }else {
                    infoQueryWrapper.eq("status",info.getStatus());
                }
            }
        }
        infoQueryWrapper.orderByDesc("create_date");
        Page<GwScenicInfo> gwScenicInfoPage = infoMapper.selectPage(page, infoQueryWrapper);
        List<GwScenicInfo> records = gwScenicInfoPage.getRecords();
        records.forEach(item ->{
            String cateName=item.getCateId() ==1 ? "新闻动态" : "景区资讯";
            item.setCateName(cateName);

            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);
        });
        return gwScenicInfoPage;
    }

    @Override
    public void setStatus(String id, Integer status) {
        GwScenicInfo gwScenicInfo = infoMapper.selectById(id);
        if (gwScenicInfo !=null){
            gwScenicInfo.setStatus(status);
            updateById(gwScenicInfo);
        }
    }
}

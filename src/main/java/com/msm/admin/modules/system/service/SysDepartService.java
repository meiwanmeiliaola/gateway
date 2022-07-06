package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysDepart;

/**
 * @Description: 单位
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
public interface SysDepartService extends IService<SysDepart> {
//    List<DepartVO> voTreeList();
//    List<DepartVO> voList();
    String getNameById(String id);

    IPage pageSearch(Page<SysDepart> page, SysDepart sysDepart);
}

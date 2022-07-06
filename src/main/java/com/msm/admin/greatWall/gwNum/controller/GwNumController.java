package com.msm.admin.greatWall.gwNum.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.gwCulture.entity.CultureCategory;
import com.msm.admin.greatWall.gwNews.entity.GwNews;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.gwNum.service.GwNumService;
import com.msm.admin.greatWall.gwRelic.service.impl.GwRelicServiceImpl;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.utils.DateUtil;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 数字长城(GwNum)表控制层
 *
 * @author makejava
 * @since 2022-02-11 14:56:52
 */
@RestController
@RequestMapping("/gwNum")
public class GwNumController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private GwNumService gwNumService;




    @PutMapping
    public GwNum save(@RequestBody GwNum gwNum){
        QueryWrapper<GwNum> wrapper=new QueryWrapper<>();

        //判断部门是否存在
        SysUser user = SubjectUtils.getCurrentUser();
        if (StringUtils.isEmpty(gwNum.getDepId())){
            gwNum.setDepId(user.getDepId());
            wrapper.eq("dep_id",user.getDepId());
        }else {
            wrapper.eq("dep_id",gwNum.getDepId());
        }

        List<Object> list;
        wrapper.eq("title",gwNum.getTitle());
        if(StringUtils.isNotEmpty(gwNum.getCreateDate())){
            gwNum.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            gwNum.setCreateDate(DateUtil.getNowTime());
        }
        if (gwNum.getStatus() ==null){
            gwNum.setStatus(0);
        }

        if (StringUtils.isNotEmpty(gwNum.getId())){
            wrapper.ne("id",gwNum.getId());
            list= gwNumService.listObjs(wrapper);
        }else {
            list=gwNumService.listObjs(wrapper);
        }

        boolean flag = list.size() ==0;
        if (flag){
            gwNumService.saveOrUpdate(gwNum);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }


        return gwNum;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        gwNumService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public GwNum getById(@PathVariable String id){
        GwNum gwNum = gwNumService.getById(id);
        return gwNum;
    }

    @GetMapping(value = "/query")
    public GwNum query(String area,String title){
          QueryWrapper<GwNum> wrapper=new QueryWrapper<>();
/*          if (StringUtils.isNotEmpty(area)){
              wrapper.eq("area",area);
          }*/
          if (StringUtils.isNotEmpty(title)){
              wrapper.eq("title",title);
          }
        GwNum gwNum = gwNumService.getOne(wrapper);
        return gwNum;
    }

    @GetMapping(value = "/list")
    public IPage page(Page<GwNum> page,GwNum gwNum){
        return gwNumService.pageSearch(page,gwNum);
    }

    @GetMapping(value = "/area")
    public List<CultureCategory> gwNumList(){
        List<CultureCategory> categoryList =new LinkedList<>();
        List<String> list= Arrays.asList("秦皇岛","唐山","承德", "张家口","保定");
        for (int i = 0; i < list.size(); i++) {
            CultureCategory category=new CultureCategory();
            Integer num=i+1;
            category.setId(num.toString());
            category.setName(list.get(i));
            categoryList.add(category);
        }
        return categoryList;
    }

    @GetMapping(value = "/review/{id}/{status}")
    public void status(@PathVariable String id,@PathVariable Integer status){
        gwNumService.steStatus(id,status);
    }

}


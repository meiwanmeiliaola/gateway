package com.msm.admin.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.analysis.entity.VisitView;
import com.msm.admin.modules.analysis.mapper.StatViewMapper;
import com.msm.admin.modules.analysis.mapper.VisitViewMapper;
import com.msm.admin.modules.analysis.service.StatViewService;
import com.msm.admin.modules.analysis.service.VisitViewService;
import com.msm.admin.modules.statistics.entity.ChartData;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: View
 * @Author: quavario
 * @Date: 2019-05-30
 * @Version: V1.0
 */
@Service
public class VisitViewServiceImpl extends ServiceImpl<VisitViewMapper, VisitView> implements VisitViewService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VisitViewMapper visitViewMapper;

    @Override
    public boolean save(VisitView entity) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        entity.setRecordDate(new Date()).setVisitorIp(getIpAddr(request))
                .setBrowser(userAgent.getBrowser().getName())
                .setOs(userAgent.getOperatingSystem().getName())
                .setDevice(userAgent.getOperatingSystem().getDeviceType().getName())
                .setUserAgent(request.getHeader("User-Agent"));
        return super.save(entity);
    }


    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }



    /**
     * 访问量
     * @param nearDay 统计的最近访问天数 nearDay = 0 查询全部
     * @return 近nearDay天访问量
     */
    @Override
    public Object getPageViewCountByNearDay(Integer nearDay) {
        QueryWrapper<VisitView> queryWrapper = new QueryWrapper<>();
        if (nearDay != null && nearDay != 0) {
            // 最近天数的时间戳
            Long timeStamp = nearDay * 24 * 60 * 60 * 1000L;

            // 获取今日零时的时间戳
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            long timeInMillis = calendar.getTimeInMillis();

            Date date = new Date(timeInMillis - timeStamp);
            queryWrapper.ge("record_date", date);
        }
        int count = count(queryWrapper);
        return count;
    }

    @Override
    public Object getUserViewCountByNearDay(Integer nearDay) {
        Date date = null;
        if (nearDay != null && nearDay != 0) {
            // 最近天数的时间戳
            Long timeStamp = nearDay * 24 * 60 * 60 * 1000L;

            // 获取今日零时的时间戳
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            long timeInMillis = calendar.getTimeInMillis();

            date = new Date(timeInMillis - timeStamp);
        }
        int count = visitViewMapper.selectUserViewCountByNearDay(date);
        return count;
    }

    @Override
    public List<ChartData> selectByDevice() {
        return visitViewMapper.selectByDevice();
    }
}

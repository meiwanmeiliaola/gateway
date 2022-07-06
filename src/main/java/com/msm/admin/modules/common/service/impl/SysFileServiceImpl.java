package com.msm.admin.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.SysFile;
import com.msm.admin.modules.common.mapper.SysFileMapper;
import com.msm.admin.modules.common.service.SysFileService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.service.RelicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author quavario@gmail.com
 * @date 2019/4/18 15:37
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Autowired
    private RelicService relicService;

    @Override
    public boolean del(String url, String type) {
        SysFile sysFile = getOne(Wrappers.<SysFile>lambdaQuery().eq(SysFile::getUrlPath, url));
        if (sysFile != null) {
            File file = new File(sysFile.getLocalPath());
            if (file.exists() && file.isFile()) {
                file.delete();
                return true;
            }
        }
        return false;
    }
}

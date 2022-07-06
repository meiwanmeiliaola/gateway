package com.msm.admin.modules.common.entity;

import com.msm.admin.common.enums.ErrorInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ResultBean<T> {



    /**
     * 返回的信息(主要出错的时候使用)
     */
    private String msg;


    /**
     * 接口返回码, 0表示成功, 其他看对应的定义
     * 晓风轻推荐的做法是:
     * 0   : 成功
     * >0 : 表示已知的异常(例如提示错误等, 需要调用地方单独处理)
     * <0 : 表示未知的异常(不需要单独处理, 调用方统一处理)
     */
    private int code;

    /**
     * 返回的数据
     */
    private T data;

    public static ResultBean build(Object body) {
        return new ResultBean<>(body);
    }

    /**
     * 错误信息
     * @param errorInfo 错误信息
     * @return 错误信息结果
     */
    public static ResultBean error(ErrorInfo errorInfo) {
        ResultBean<Object> resultBean = new ResultBean<>();
        resultBean.setCode(errorInfo.getCode());
        resultBean.setMsg(errorInfo.getMessage());
        return resultBean;
    }

    public static ResultBean build() {
        return new ResultBean<>();
    }

    public ResultBean(T data) {
        this.data = data;
    }
}

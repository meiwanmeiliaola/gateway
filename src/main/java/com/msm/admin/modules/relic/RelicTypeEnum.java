package com.msm.admin.modules.relic;

/**
 * @author quavario@gmail.com
 * @date 2020/1/9 16:56
 */
public enum RelicTypeEnum {
    COMMON(1),
    TREASURE(2),
    R3D(3),
    REV_COMMON(4),
    REV_TREASURE(5),
    REV_R3D(6),
    ALL_REV(456);

    private int code;

    RelicTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }}

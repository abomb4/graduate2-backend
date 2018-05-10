package org.wlyyy.itrs.dict;

/**
 * 需求状态枚举。
 *
 * @author wly
 */
public enum EnumDemandStatus implements IDictionary<Integer> {

    NORMAL(1, "normal", "正常"),
    DELETED(0, "deleted", "已下架"),
    ;

    private final Integer code;
    private final String name;
    private final String desc;

    EnumDemandStatus(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer code) {
        EnumDemandStatus[] values = EnumDemandStatus.values();
        for (EnumDemandStatus enumDemandStatus : values) {
            if (enumDemandStatus.getCode().equals(code)) {
                return enumDemandStatus.getDesc();
            }
        }
        return "";
    }
}

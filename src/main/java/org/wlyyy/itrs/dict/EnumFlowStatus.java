package org.wlyyy.itrs.dict;

/**
 * 流程状态枚举
 */
public enum EnumFlowStatus  implements IDictionary<Integer> {


    EXECUTION(1, "execution", "执行中"),
    FINISH(2, "finish", "结束"),
    EXCEPTION(3, "exception", "异常"),
            ;

    private final Integer code;
    private final String name;
    private final String desc;

    EnumFlowStatus(Integer code, String name, String desc) {
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
        EnumFlowStatus[] values = EnumFlowStatus.values();
        for (EnumFlowStatus enumFlowStatus : values) {
            if (enumFlowStatus.getCode().equals(code)) {
                return enumFlowStatus.getDesc();
            }
        }
        return "";
    }
}

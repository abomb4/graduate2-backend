package org.wlyyy.itrs.dict;

/**
 * 定义数据库中表名
 */
public enum EnumTableName implements IDictionary<String> {

    APPLY_FLOW("applyFlow", "apply_flow", "招聘流程信息表"),
    ;

    private final String code;
    private final String name;
    private final String desc;

    EnumTableName(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getCode() {
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
}


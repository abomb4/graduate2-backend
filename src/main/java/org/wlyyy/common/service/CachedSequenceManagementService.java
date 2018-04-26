package org.wlyyy.common.service;

/**
 * 序列号生成接口，负责生成用于拼装流水号的序列号，要保证整个业务系统短时间内唯一。
 * 所有的流水号不要使用自己的DAO或其他什么实现方式，统一用这个接口
 * 
 * @author yangrl14628 2016-08-25
 * 
 */
public interface CachedSequenceManagementService {

    /**
     * 性能较低的取值方法
     * @param key
     * @return
     */
    Long getBySequenceType(String key);
}

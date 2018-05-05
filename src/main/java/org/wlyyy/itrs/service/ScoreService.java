package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServiceResponse;

public interface ScoreService {

    /**
     * 积分变动处理
     *
     * @param rule 规则，对应传过来的result
     * @param userId 用户id
     * @param memo 积分变动原因
     * @return 成功or失败
     */
    BaseServiceResponse<String> changeScore(String rule, Long userId, String memo);
}

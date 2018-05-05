package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.ScoreRule;
import org.wlyyy.itrs.request.ScoreRuleQuery;

/**
 * 积分规则表基本管理服务
 */
public interface ScoreRuleService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<ScoreRule> findByCondition(BaseServicePageableRequest<ScoreRuleQuery> request);

    /**
     * 根据积分规则id查询
     *
     * @param id 积分规则id
     * @return 积分规则，没有的话返回null
     */
    ScoreRule findById(Long id);

    /**
     * 根据规则查询相应积分
     *
     * @param rule
     * @return
     */
    Integer findScoreByRule(String rule);

    /**
     * 插入积分规则
     *
     * @param scoreRule 积分规则信息
     * @return id 插入的积分规则id
     */
    BaseServiceResponse<Long> insertScoreRule(ScoreRule scoreRule);

    /**
     * 更新积分规则
     *
     * @param scoreRule 更新的积分规则，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateScoreRule(ScoreRule scoreRule);
}

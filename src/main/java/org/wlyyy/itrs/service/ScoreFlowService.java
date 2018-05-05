package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.ScoreFlow;
import org.wlyyy.itrs.request.ScoreFlowQuery;

/**
 * 积分流水变动记录表基本管理服务
 */
public interface ScoreFlowService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<ScoreFlow> findByCondition(BaseServicePageableRequest<ScoreFlowQuery> request);

    /**
     * 根据积分流水变动记录id查询
     *
     * @param id 积分流水变动记录id
     * @return 积分流水变动记录，没有的话返回null
     */
    ScoreFlow findById(Long id);

    /**
     * 插入积分流水变动记录
     *
     * @param scoreFlow 积分流水变动记录信息
     * @return id 插入的积分流水变动记录id
     */
    BaseServiceResponse<Long> insertScoreFlow(ScoreFlow scoreFlow);

    /**
     * 更新积分流水变动记录
     *
     * @param scoreFlow 更新的积分流水变动记录，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateScoreFlow(ScoreFlow scoreFlow);
}

package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;

import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.request.ApplyFlowQuery;


/**
 * 招聘流程信息表基本管理服务
 */
public interface ApplyFlowService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<ApplyFlow> findByCondition(BaseServicePageableRequest<ApplyFlowQuery> request);

    /**
     * 根据招聘流程id查询
     *
     * @param id 招聘流程id
     * @return 招聘流程信息，没有的话返回null
     */
    ApplyFlow findById(Long id);

    /**
     * 插入招聘流程
     *
     * @param applyFlow 招聘流程
     * @return id
     */
    BaseServiceResponse<Long> insertApplyFlow(ApplyFlow applyFlow);

    /**
     * 更新招聘流程
     *
     * @param applyFlow 更新的招聘流程，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateApplyFlow(ApplyFlow applyFlow);
}

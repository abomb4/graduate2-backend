package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.request.CandidateQuery;

/**
 * 被推荐人信息表基本管理服务
 */
public interface CandidateService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<Candidate> findByCondition(BaseServicePageableRequest<CandidateQuery> request);

    /**
     * 根据被推荐人id查询
     *
     * @param id 被推荐人id
     * @return 被推荐人信息，没有的话返回null
     */
    Candidate findById(Long id);

    /**
     * 插入被推荐人信息
     *
     * @param candidate 被推荐人信息
     * @return id 插入的被推荐人id
     */
    BaseServiceResponse<Long> insertCandidate(Candidate candidate);

    /**
     * 更新被推荐人信息
     *
     * @param candidate 更新的被推荐人信息，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateCandidate(Candidate candidate);
}

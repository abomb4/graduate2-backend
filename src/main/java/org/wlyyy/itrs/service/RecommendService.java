package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Recommend;
import org.wlyyy.itrs.request.RecommendQuery;

/**
 * 推荐表基本管理服务
 */
public interface RecommendService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<Recommend> findByCondition(BaseServicePageableRequest<RecommendQuery> request);

    /**
     * 根据推荐表id查询
     *
     * @param id 推荐表id
     * @return 推荐表，没有的话返回null
     */
    Recommend findById(Long id);

    /**
     * 插入推荐表
     *
     * @param recommend 推荐表
     * @return id 插入的推荐表id
     */
    BaseServiceResponse<Long> insertRecommend(Recommend recommend);

    /**
     * 更新推荐表
     *
     * @param recommend 更新的推荐表，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateRecommend(Recommend recommend);
}
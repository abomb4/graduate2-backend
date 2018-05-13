package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.request.DemandQuery;

import java.util.List;

/**
 * 招聘需求服务，提供基本查询增加删除功能以及状态修改接口。
 */
public interface DemandService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<Demand> findByCondition(BaseServicePageableRequest<DemandQuery> request);

    /**
     * 分页查找publisher_id在某集合中的招聘需求列表
     *
     * @param request 分页查询条件
     * @param publisherIds 招聘需求发布者id集合
     * @return 查询结果
     */
    BaseServicePageableResponse<Demand> findByFollowing(BaseServicePageableRequest<DemandQuery> request, List<Long> publisherIds);

    /**
     * 根据招聘需求id查询
     *
     * @param id 被推荐人id
     * @return 招聘需求，没有的话返回null
     */
    Demand findById(Long id);

    /**
     * 根据招聘需求id查询
     *
     * @param demandNo 招聘需求No
     * @return 招聘需求，没有的话返回null
     */
    Demand findByNo(String demandNo);

    /**
     * 插入招聘需求
     *
     * @param demand 招聘需求
     * @return id 插入的招聘需求id
     */
    BaseServiceResponse<Long> insertDemand(Demand demand);

    /**
     * 更新招聘需求
     *
     * @param demand 更新的招聘需求，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateDemand(Demand demand);

    /**
     * 逻辑删除需求
     *
     * @param id 需求id
     * @return 删除行数
     */
    BaseServiceResponse<Integer> deleteDemandLogically(Long id);
}

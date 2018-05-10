package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.DemandRepository;
import org.wlyyy.itrs.dict.EnumDemandStatus;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.request.DemandQuery;

import java.util.List;

@Service
public class DemandServiceImpl implements DemandService {

    @Autowired
    private DemandRepository dao;

    @Override
    public BaseServicePageableResponse<Demand> findByCondition(BaseServicePageableRequest<DemandQuery> request) {
        // Sort用法：
        // Sort sort = new Sort(new Order(Sort.Direction.DESC, "demandNo"), new Order("total"));
        // Sort sort = request.getData().getSort();
        // final Pageable pageable = PageableUtils.getPageable(request, sort);

        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }
        final List<Demand> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query demand success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public Demand findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Demand findByNo(String demandNo) {
        return dao.findByNo(demandNo);
    }

    @Override
    public BaseServiceResponse<Long> insertDemand(Demand demand) {
        dao.insert(demand);
        final Long id = demand.getId();
        return new BaseServiceResponse<>(true, "Insert demand success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateDemand(Demand demand) {
        int updateCount = dao.updateById(demand);
        return new BaseServiceResponse<>(true, "Update demand success!", updateCount, null);
    }

    @Override
    public BaseServiceResponse<Integer> deleteDemandLogically(Long id) {
        Demand demand = new Demand();
        demand.setId(id);
        demand.setStatus(EnumDemandStatus.DELETED.getCode());
        int deleteCount = dao.updateById(demand);
        return new BaseServiceResponse<>(true, "Logically deleted demand success!", deleteCount, null);
    }
}

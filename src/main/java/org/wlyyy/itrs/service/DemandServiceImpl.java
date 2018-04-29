package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.DemandRepository;
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
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<Demand> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (queryResult.size() < request.getPageSize()) {
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
        return null;
    }
}

package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.ApplyFlowRepository;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.request.ApplyFlowQuery;

import java.util.List;

@Service
public class ApplyFlowServiceImpl implements ApplyFlowService{

    @Autowired
    private ApplyFlowRepository dao;

    @Override
    public BaseServicePageableResponse<ApplyFlow> findByCondition(BaseServicePageableRequest<ApplyFlowQuery> request) {

        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }
        final List<ApplyFlow> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query apply_flow success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public BaseServicePageableResponse<ApplyFlow> findNotInDemandNo(BaseServicePageableRequest<ApplyFlowQuery> request, List<String> demandNoList) {
        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }
        final List<ApplyFlow> queryResult = dao.findNotInDemandNo(request.getData(), pageable, demandNoList);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countNotInDemandNo(request.getData(), demandNoList);
        }

        return new BaseServicePageableResponse<>(
                true, "Query apply_flow success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public ApplyFlow findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<Long> insertApplyFlow(ApplyFlow applyFlow) {
        dao.insert(applyFlow);
        final Long id = applyFlow.getId();
        return new BaseServiceResponse<>(true, "Insert apply_flow success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateApplyFlow(ApplyFlow applyFlow) {
        int updateCount = dao.updateById(applyFlow);
        return new BaseServiceResponse<>(true, "Update apply_flow success!", updateCount, null);
    }
}

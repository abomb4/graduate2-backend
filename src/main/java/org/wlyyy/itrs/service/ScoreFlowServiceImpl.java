package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.ScoreFlowRepository;
import org.wlyyy.itrs.domain.ScoreFlow;
import org.wlyyy.itrs.request.ScoreFlowQuery;

import java.util.List;

@Service
public class ScoreFlowServiceImpl implements ScoreFlowService{

    @Autowired
    private ScoreFlowRepository dao;

    @Override
    public BaseServicePageableResponse<ScoreFlow> findByCondition(BaseServicePageableRequest<ScoreFlowQuery> request) {
        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }

        final List<ScoreFlow> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query scoreFlow success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public ScoreFlow findById(Long id) {
        return dao.findById(id);
    }



    @Override
    public BaseServiceResponse<Long> insertScoreFlow(ScoreFlow scoreFlow) {
        dao.insert(scoreFlow);
        final Long id = scoreFlow.getId();
        return new BaseServiceResponse<>(true, "Insert scoreFlow success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateScoreFlow(ScoreFlow scoreFlow) {
        int updateCount = dao.updateById(scoreFlow);
        return new BaseServiceResponse<>(true, "Update scoreFlow success!", updateCount, null);
    }

}

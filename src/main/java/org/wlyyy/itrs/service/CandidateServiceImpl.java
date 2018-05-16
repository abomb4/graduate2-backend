package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.CandidateRepository;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.request.CandidateQuery;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService{

    @Autowired
    private CandidateRepository dao;

    @Override
    public BaseServicePageableResponse<Candidate> findByCondition(BaseServicePageableRequest<CandidateQuery> request) {
        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }
        final List<Candidate> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query candidate success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public Candidate findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<Long> insertCandidate(Candidate candidate) {
        dao.insert(candidate);
        final Long id = candidate.getId();
        return new BaseServiceResponse<>(true, "Insert candidate success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateCandidate(Candidate candidate) {
        int updateCount = dao.updateById(candidate);
        return new BaseServiceResponse<>(true, "Update candidate success!", updateCount, null);
    }
}

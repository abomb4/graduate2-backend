package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.RecommendRepository;
import org.wlyyy.itrs.domain.Recommend;
import org.wlyyy.itrs.request.RecommendQuery;

import java.util.List;

public class RecommendServiceImpl implements  RecommendService{

    @Autowired
    private RecommendRepository dao;

    @Override
    public BaseServicePageableResponse<Recommend> findByCondition(BaseServicePageableRequest<RecommendQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<Recommend> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (queryResult.size() < request.getPageSize()) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query recommend success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public Recommend findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<Long> insertRecommend(Recommend recommend) {
        dao.insert(recommend);
        final Long id = recommend.getId();
        return new BaseServiceResponse<>(true, "Insert recommend success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateRecommend(Recommend recommend) {
        int updateCount = dao.updateById(recommend);
        return new BaseServiceResponse<>(true, "Update recommend success!", updateCount, null);
    }
}

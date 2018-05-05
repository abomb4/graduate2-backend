package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.ScoreRuleRepository;
import org.wlyyy.itrs.domain.ScoreRule;
import org.wlyyy.itrs.request.ScoreRuleQuery;

import java.util.List;

@Service
public class ScoreRuleServiceImpl implements ScoreRuleService{

    @Autowired
    private ScoreRuleRepository dao;

    @Override
    public BaseServicePageableResponse<ScoreRule> findByCondition(BaseServicePageableRequest<ScoreRuleQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<ScoreRule> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query scoreRule success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public ScoreRule findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Integer findScoreByRule(String rule) {
        return dao.findScoreByRule(rule);
    }

    @Override
    public BaseServiceResponse<Long> insertScoreRule(ScoreRule scoreRule) {
        dao.insert(scoreRule);
        final Long id = scoreRule.getId();
        return new BaseServiceResponse<>(true, "Insert scoreRule success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateScoreRule(ScoreRule scoreRule) {
        int updateCount = dao.updateById(scoreRule);
        return new BaseServiceResponse<>(true, "Update scoreRule success!", updateCount, null);
    }
}



package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.ScoreUserRepository;
import org.wlyyy.itrs.domain.ScoreUser;
import org.wlyyy.itrs.request.ScoreUserQuery;

import java.util.List;

@Service
public class ScoreUserServiceImpl implements ScoreUserService{

    @Autowired
    private ScoreUserRepository dao;

    @Override
    public BaseServicePageableResponse<ScoreUser> findByCondition(BaseServicePageableRequest<ScoreUserQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<ScoreUser> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query scoreUser success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public ScoreUser findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public ScoreUser findByUserId(Long userId) {
        return dao.findCurrentScoreByUserId(userId);
    }

    @Override
    public BaseServiceResponse<Long> insertScoreUser(ScoreUser scoreUser) {
        dao.insert(scoreUser);
        final Long id = scoreUser.getId();
        return new BaseServiceResponse<>(true, "Insert scoreUser success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateScoreUser(ScoreUser scoreUser) {
        int updateCount = dao.updateById(scoreUser);
        return new BaseServiceResponse<>(true, "Update scoreUser success!", updateCount, null);
    }
}

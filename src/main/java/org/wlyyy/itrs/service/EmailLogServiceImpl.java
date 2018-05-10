package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.EmailLogRepository;
import org.wlyyy.itrs.domain.EmailLog;
import org.wlyyy.itrs.request.EmailLogQuery;

import java.util.List;

@Service
public class EmailLogServiceImpl implements EmailLogService{

    @Autowired
    private EmailLogRepository dao;

    @Override
    public BaseServicePageableResponse<EmailLog> findByCondition(BaseServicePageableRequest<EmailLogQuery> request) {
        Pageable pageable = null;
        if (request.getData().getSort() != null) {
            pageable = PageableUtils.getPageable(request, request.getData().getSort());
        } else {
            pageable = PageableUtils.getPageable(request);
        }

        final List<EmailLog> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query emailLog success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public EmailLog findById(Long id) {
        return dao.findById(id);
    }



    @Override
    public BaseServiceResponse<Long> insertEmailLog(EmailLog emailLog) {
        dao.insert(emailLog);
        final Long id = emailLog.getId();
        return new BaseServiceResponse<>(true, "Insert emailLog success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateEmailLog(EmailLog emailLog) {
        int updateCount = dao.updateById(emailLog);
        return new BaseServiceResponse<>(true, "Update emailLog success!", updateCount, null);
    }
}

package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.EmailLog;
import org.wlyyy.itrs.request.EmailLogQuery;

/**
 * 邮件日志表基本管理服务
 */
public interface EmailLogService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<EmailLog> findByCondition(BaseServicePageableRequest<EmailLogQuery> request);

    /**
     * 根据邮件日志id查询
     *
     * @param id 邮件日志id
     * @return 邮件日志，没有的话返回null
     */
    EmailLog findById(Long id);

    /**
     * 插入邮件日志
     *
     * @param emailLog 邮件日志信息
     * @return id 插入的邮件日志id
     */
    BaseServiceResponse<Long> insertEmailLog(EmailLog emailLog);

    /**
     * 更新邮件日志
     *
     * @param emailLog 更新的邮件日志，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateEmailLog(EmailLog emailLog);
}

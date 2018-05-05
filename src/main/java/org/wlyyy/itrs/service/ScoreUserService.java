package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.ScoreUser;
import org.wlyyy.itrs.request.ScoreUserQuery;

/**
 * 用户当前积分表基本管理服务
 */

public interface ScoreUserService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<ScoreUser> findByCondition(BaseServicePageableRequest<ScoreUserQuery> request);

    /**
     * 根据用户当前积分id查询
     *
     * @param id 用户当前积分id
     * @return 用户当前积分，没有的话返回null
     */
    ScoreUser findById(Long id);

    /**
     * 根据用户id查询其当前积分对象
     *
     * @param userId 用户id
     * @return 用户当前积分，没有的话返回null
     */
    ScoreUser findByUserId(Long userId);

    /**
     * 插入用户当前积分
     *
     * @param scoreUser 用户当前积分信息
     * @return id 插入的用户当前积分id
     */
    BaseServiceResponse<Long> insertScoreUser(ScoreUser scoreUser);

    /**
     * 更新用户当前积分
     *
     * @param scoreUser 更新的用户当前积分，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateScoreUser(ScoreUser scoreUser);
}

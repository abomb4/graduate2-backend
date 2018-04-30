package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.request.RoleQuery;

/**
 * 角色信息表基本管理服务
 */
public interface RoleService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<Role> findByCondition(BaseServicePageableRequest<RoleQuery> request);

    /**
     * 根据角色id查询
     *
     * @param id 角色id
     * @return 角色信息，没有的话返回null
     */
    Role findById(Long id);

    /**
     * 插入角色信息
     *
     * @param role 角色信息
     * @return id 插入的角色id
     */
    BaseServiceResponse<Long> insertRole(Role role);

    /**
     * 更新角色信息
     *
     * @param role 更新的角色信息，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateRole(Role role);
}

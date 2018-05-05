package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.RoleRepository;
import org.wlyyy.itrs.dao.UserRoleRepository;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.domain.UserRole;
import org.wlyyy.itrs.request.RoleQuery;
import org.wlyyy.itrs.request.UserRoleQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements  RoleService{

    @Autowired
    private RoleRepository dao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Override
    public BaseServicePageableResponse<Role> findByCondition(BaseServicePageableRequest<RoleQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<Role> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query role success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public Role findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<Long> insertRole(Role role) {
        dao.insert(role);
        final Long id = role.getId();
        return new BaseServiceResponse<>(true, "Insert role success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateRole(Role role) {
        int updateCount = dao.updateById(role);
        return new BaseServiceResponse<>(true, "Update role success!", updateCount, null);
    }

    @Override
    public BaseServiceResponse<Set<Long>> findUserIdsByRoleId(Long roleId) {
        final List<UserRole> queryResult = userRoleDao.findByCondition(new UserRoleQuery().setRoleId(roleId),
                new PageRequest(0, Integer.MAX_VALUE));
        final Set<Long> querySet = queryResult.stream().map(userRole -> userRole.getUserId()).collect(Collectors.toSet());

        return new BaseServiceResponse<>(true, "Query userIds by roleId success!", querySet, null);
    }

    @Override
    public BaseServiceResponse<Set<Role>> findRoleIdsByUserId(Long userId) {
        final List<UserRole> queryResult = userRoleDao.findByCondition(new UserRoleQuery().setUserId(userId),
                new PageRequest(0, Integer.MAX_VALUE));
        final Set<Long> querySet = queryResult.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toSet());
        final Set<Role> roleSet = querySet.stream().map(roleId -> dao.findById(roleId)).collect(Collectors.toSet());
        return new BaseServiceResponse<>(true, "Query roleIds by userId success!", roleSet, null);
    }

    @Transactional
    @Override
    public BaseServiceResponse<String> updateUserRole(Long userId, Set<Long> roleIds) {
        // 先删除该用户的所有角色id
        long deleteCount = userRoleDao.deleteByUserID(userId);
        // 再插入新角色id们
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleDao.insert(userRole);
        });
        return new BaseServiceResponse<>(true, "Update user role success!", null, null);
    }
}

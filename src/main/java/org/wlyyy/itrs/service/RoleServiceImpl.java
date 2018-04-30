package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.RoleRepository;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.request.RoleQuery;

import java.util.List;

@Service
public class RoleServiceImpl implements  RoleService{

    @Autowired
    private RoleRepository dao;

    @Override
    public BaseServicePageableResponse<Role> findByCondition(BaseServicePageableRequest<RoleQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<Role> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (queryResult.size() < request.getPageSize()) {
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
}

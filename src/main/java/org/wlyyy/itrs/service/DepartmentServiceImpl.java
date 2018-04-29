package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.DepartmentRepository;
import org.wlyyy.itrs.domain.Department;
import org.wlyyy.itrs.request.DepartmentQuery;

import java.util.List;

public class DepartmentServiceImpl implements  DepartmentService{

    @Autowired
    private DepartmentRepository dao;

    @Override
    public BaseServicePageableResponse<Department> findByCondition(BaseServicePageableRequest<DepartmentQuery> request) {
        final Pageable pageable = PageableUtils.getPageable(request);
        final List<Department> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (queryResult.size() < request.getPageSize()) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query department success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public Department findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<Long> insertDepartment(Department department) {
        dao.insert(department);
        final Long id = department.getId();
        return new BaseServiceResponse<>(true, "Insert department success!", id, null);
    }

    @Override
    public BaseServiceResponse<Integer> updateDepartment(Department department) {
        int updateCount = dao.updateById(department);
        return new BaseServiceResponse<>(true, "Update department success!", updateCount, null);
    }
}

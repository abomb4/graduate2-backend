package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Department;
import org.wlyyy.itrs.request.DepartmentQuery;

public interface DepartmentService {

    /**
     * 条件分页查询
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<Department> findByCondition(BaseServicePageableRequest<DepartmentQuery> request);

    /**
     * 根据组织机构id查询
     *
     * @param id 组织机构id
     * @return 组织机构信息，没有的话返回null
     */
    Department findById(Long id);

    /**
     * 插入组织机构信息
     *
     * @param department 组织机构信息
     * @return id 插入的组织机构id
     */
    BaseServiceResponse<Long> insertDepartment(Department department);

    /**
     * 更新组织机构信息
     *
     * @param department 更新的组织机构信息，其中id必传，其余至少传一项
     * @return 更新数目
     */
    BaseServiceResponse<Integer> updateDepartment(Department department);
}

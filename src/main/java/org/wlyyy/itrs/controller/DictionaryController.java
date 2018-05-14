package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.Department;
import org.wlyyy.itrs.domain.PositionType;
import org.wlyyy.itrs.request.DemandQuery;
import org.wlyyy.itrs.request.DepartmentQuery;
import org.wlyyy.itrs.service.DepartmentService;
import org.wlyyy.itrs.service.PositionService;

import java.util.List;

@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询职位类别树
     *
     * @return 职位类别树
     */
    @RequestMapping(value = "/positionTypeTree", method = RequestMethod.GET)
    public BaseRestResponse<List<PositionType>> findPositionTypeTree() {
        List<PositionType> treeTypes = positionService.getPositionTypes();
        return new BaseRestResponse<>(true, "查询职位类别树成功!", treeTypes);
    }

    /**
     * 查询所有部门
     *
     * @return 所有部门列表
     */
    @RequestMapping(value = "/departmentList", method = RequestMethod.GET)
    public BaseRestResponse<List<Department>> findAllDepartment() {
        final BaseServicePageableRequest<DepartmentQuery> request = new BaseServicePageableRequest<>(1, Integer.MAX_VALUE, new DepartmentQuery());
        final BaseServicePageableResponse<Department> response = departmentService.findByCondition(request);

        return new BaseRestResponse<>(response.isSuccess(), response.getMessage(), response.getDatas());
    }
}

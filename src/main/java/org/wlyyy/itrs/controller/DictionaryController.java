package org.wlyyy.itrs.controller;

import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.Department;
import org.wlyyy.itrs.domain.PositionType;
import org.wlyyy.itrs.request.DepartmentQuery;
import org.wlyyy.itrs.request.WorkFlowQuery;
import org.wlyyy.itrs.service.DepartmentService;
import org.wlyyy.itrs.service.PositionService;
import org.wlyyy.itrs.service.WorkFlowService;
import org.wlyyy.itrs.vo.DeploymentListItemVo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorkFlowService workFlowService;

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

    /**
     * 查询所有部署流程
     *
     * @return 所有部署流程列表
     */
    @RequestMapping(value = "/prokey", method = RequestMethod.GET)
    public BaseRestResponse<List<DeploymentListItemVo>> findAllProcKey() {
        final int pageNo = 1;
        final int pageSize = Integer.MAX_VALUE;
        BaseServicePageableResponse<Deployment> deploymentResult = workFlowService.findAllDeploy(new BaseServicePageableRequest<>(pageNo, pageSize,
                new WorkFlowQuery()));
        List<Deployment> deploymentList = deploymentResult.getDatas();
        List<DeploymentListItemVo> datas = deploymentList.stream().map(deployment -> DeploymentListItemVo.buildFromDomain(deployment,
                id -> workFlowService.findKeyByDeploymentId(id).getData().getKey()))
                .collect(Collectors.toList());
        return new BaseRestResponse(true, "查询流程部署key成功", datas);
    }
}

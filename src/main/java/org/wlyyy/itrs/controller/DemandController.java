package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.request.DemandQuery;
import org.wlyyy.itrs.service.DemandService;
import org.wlyyy.itrs.service.DepartmentService;
import org.wlyyy.itrs.service.UserService;
import org.wlyyy.itrs.vo.DemandListItemVo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("demand")
public class DemandController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DemandService demandService;

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DemandController.class);

    @RequestMapping(value = "/list")
    public BaseRestPageableResponse<DemandListItemVo> queryDemandList(DemandQuery demandQuery, int pageNo, int pageSize) {

        BaseServicePageableRequest<DemandQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize, demandQuery);
        BaseServicePageableResponse<Demand> demandResult =  demandService.findByCondition(request);

        if (!demandResult.isSuccess()) {
            return new BaseRestPageableResponse<>(false, "查询招聘需求列表失败!", null,
                    demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
        }

        List<Demand> demandList = demandResult.getDatas();
        List<DemandListItemVo> datas = demandList.stream().map(source -> DemandListItemVo.buildFromDomain(source,
                (pid) -> userService.findById(pid).getUserName(),
                (did) -> departmentService.findById(did).getDepartmentName())).collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询招聘需求列表成功!", datas,
                demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
    }
}

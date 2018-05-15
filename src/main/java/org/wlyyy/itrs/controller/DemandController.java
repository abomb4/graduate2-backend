package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.*;
import org.wlyyy.common.service.CachedSequenceManagementService;
import org.wlyyy.itrs.dict.EnumDemandStatus;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.domain.PositionType;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.request.DemandQuery;
import org.wlyyy.itrs.request.UserQuery;
import org.wlyyy.itrs.request.rest.DemandQueryRequest;
import org.wlyyy.itrs.service.*;
import org.wlyyy.itrs.vo.DemandListItemVo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DemandController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DemandService demandService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CachedSequenceManagementService cachedSequenceManagementService;

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DemandController.class);

    /**
     * 分页复杂条件查询招聘需求
     *
     * @param demandQueryRequest 查询对象
     * @param pageNo             页码
     * @param pageSize           分页大小
     * @return 分页展示层招聘需求列表
     */
    @RequestMapping(value = "/demand/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<DemandListItemVo> queryDemandList(DemandQueryRequest demandQueryRequest, int pageNo, int pageSize) {

        final DemandQuery demandQuery = demandQueryRequest.buildDemandQuery(
                id -> {
                    if (id == null) {
                        return null;
                    }
                    final PositionType positionType = positionService.findById(id);
                    if (positionType == null || positionType.getSubTypes() == null || positionType.getSubTypes().size() <= 0) {
                        return Collections.singletonList(id);
                    } else {
                        return positionType.getSubTypes().stream().map(PositionType::getId).collect(Collectors.toList());
                    }
                }
        );

        Sort sort = new Sort(new Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<DemandQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize,
                demandQuery.setStatus(EnumDemandStatus.NORMAL.getCode()).setSort(sort));
        BaseServicePageableResponse<Demand> demandResult =  demandService.findByCondition(request);

        if (!demandResult.isSuccess()) {
            return new BaseRestPageableResponse<>(false, "查询招聘需求列表失败!", null,
                    demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
        }

        List<Demand> demandList = demandResult.getDatas();
        List<DemandListItemVo> datas = demandList.stream().map(source -> DemandListItemVo.buildFromDomain(source,
                (pid) -> userService.findById(pid).getRealName(),
                (did) -> departmentService.findById(did).getDepartmentName(),
                (ptid) -> positionService.findById(ptid).getChineseName()
                )).collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询招聘需求列表成功!", datas,
                demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
    }

    /**
     * 分页查找当前用户发布的所有招聘需求
     *
     * @param pageNo 页码
     * @param pageSize 分页大小
     * @return 分页展示层招聘需求列表
     */
    @RequestMapping(value = "/myProfile/mydemand/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<DemandListItemVo> queryMyDemandList(int pageNo, int pageSize) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();

        Sort sort = new Sort( new Order(Sort.Direction.DESC, "status"), new Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<DemandQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize,
                new DemandQuery().setPublisherId(userAgent.getId()).setSort(sort));
        BaseServicePageableResponse<Demand> demandResult =  demandService.findByCondition(request);

        if (!demandResult.isSuccess()) {
            return new BaseRestPageableResponse<>(false, "查询招聘需求列表失败!", null,
                    demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
        }

        List<Demand> demandList = demandResult.getDatas();
        List<DemandListItemVo> datas = demandList.stream().map(source -> DemandListItemVo.buildFromDomain(source,
                (pid) -> userService.findById(pid).getRealName(),
                (did) -> departmentService.findById(did).getDepartmentName(),
                (ptid) -> positionService.findById(ptid).getChineseName()
        )).collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询招聘需求列表成功!", datas,
                demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
    }

    /**
     * 分页查找当前用户同部门的所有招聘需求（给部门领导用）
     *
     * @param pageNo 页码
     * @param pageSize 分页大小
     * @return 分页展示层招聘需求列表
     */
    @RequestMapping(value = "/myProfile/mydemandFollowing/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<DemandListItemVo> queryMyDemandFollowingList(int pageNo, int pageSize) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();

        // 获取同部门的用户id
        Long departmentId = userService.findById(userAgent.getId()).getDepartmentId();
        BaseServicePageableRequest<UserQuery> requestUser = new BaseServicePageableRequest<>(1, Integer.MAX_VALUE,
                new UserQuery().setDepartmentId(departmentId));
        List<User> followingUserList = userService.findByCondition(requestUser).getDatas();
        List<Long> followingUserId = followingUserList.stream().map(user -> user.getId()).collect(Collectors.toList());

        Sort sort = new Sort( new Order(Sort.Direction.DESC, "status"), new Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<DemandQuery> requestDemand = new BaseServicePageableRequest<>(pageNo, pageSize,
                new DemandQuery().setSort(sort));
        BaseServicePageableResponse<Demand> demandResult =  demandService.findByFollowing(requestDemand, followingUserId);
        List<Demand> demandList = demandResult.getDatas();
        List<DemandListItemVo> datas = demandList.stream().map(source -> DemandListItemVo.buildFromDomain(source,
                (pid) -> userService.findById(pid).getRealName(),
                (did) -> departmentService.findById(did).getDepartmentName(),
                (ptid) -> positionService.findById(ptid).getChineseName()
        )).collect(Collectors.toList());

        return new BaseRestPageableResponse<>(true, "查询招聘需求列表成功!", datas,
                demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
    }

    /**
     * 查询最新职位需求
     *
     * @return 最新展示层招聘需求列表
     */
    @RequestMapping(value = "/demand/new", method = RequestMethod.GET)
    public BaseRestPageableResponse<DemandListItemVo> queryDemandListNew() {

        Sort sort = new Sort(new Order(Sort.Direction.DESC, "gmt_modify"));
        int pageNo = 1;
        int pageSize = 8;
        BaseServicePageableRequest<DemandQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize,
                new DemandQuery().setStatus(EnumDemandStatus.NORMAL.getCode()).setSort(sort));
        BaseServicePageableResponse<Demand> demandResult =  demandService.findByCondition(request);

        if (!demandResult.isSuccess()) {
            return new BaseRestPageableResponse<>(false, "查询最新招聘需求列表失败!", null,
                    demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
        }

        List<Demand> demandList = demandResult.getDatas();
        List<DemandListItemVo> datas = demandList.stream().map(source -> DemandListItemVo.buildFromDomain(source,
                (pid) -> userService.findById(pid).getRealName(),
                (did) -> departmentService.findById(did).getDepartmentName(),
                (ptid) -> positionService.findById(ptid).getChineseName()
        )).collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询最新招聘需求列表成功!", datas,
                demandResult.getPageNo(), demandResult.getPageSize(), demandResult.getTotal());
    }

    /**
     * 发布招聘需求
     *
     * @param demand 招聘需求对象
     * @return 是否发布成功
     */
    @RequestMapping(value = "/myProfile/demand", method = RequestMethod.POST)
    public BaseRestResponse<DemandListItemVo> postDemand(Demand demand) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();

        String demandNo = cachedSequenceManagementService.getBySequenceType("seq_demand_no").toString();
        demand.setDemandNo(demandNo);
        demand.setPublisherId(userAgent.getId());
        demand.setHrName(userAgent.getRealName());
        demand.setDepartmentId(userAgent.getDepartmentId());
        demand.setStatus(EnumDemandStatus.NORMAL.getCode());

        BaseServiceResponse<Long> insertDemandResult = demandService.insertDemand(demand);
        if (!insertDemandResult.isSuccess()) {
            return new BaseRestResponse<>(false, "Create fail", null);
        }
        Long demandId = insertDemandResult.getData();
        return new BaseRestResponse<>(true, "Create success", null);
    }

    /**
     * 修改招聘需求
     *
     * @param demand 招聘需求对象
     * @return 是否修改成功
     */
    @RequestMapping(value = "myProfile/demand", method = RequestMethod.PUT)
    public BaseRestResponse<DemandListItemVo> updateDemand(Demand demand) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();
        // 非发布的hr不能修改该需求
        if (!userAgent.getId().equals(demand.getPublisherId())) {
            return new BaseRestResponse<>(false, "修改招聘需求失败!非发布的hr不能修改该需求!", null);
        }

        BaseServiceResponse<Integer> updateDemandResult = demandService.updateDemand(demand);
        if (!updateDemandResult.isSuccess()) {
            return new BaseRestResponse<>(false, "Update fail", null);
        }
        int updateCount = updateDemandResult.getData();
        return new BaseRestResponse<>(true, "Update success", null);
    }

    @RequestMapping(value = "/demand/get/{id}", method = RequestMethod.GET)
    public BaseRestResponse<DemandListItemVo> findDemandById(@PathVariable("id") Long id) {
        Demand demand = demandService.findById(id);
        if (demand == null) {
            return new BaseRestResponse<>(false, "未找到该需求", null);
        } else {
            DemandListItemVo demandListItemVo = DemandListItemVo.buildFromDomain(demand,
                    (pid) -> userService.findById(pid).getUserName(),
                    (did) -> departmentService.findById(did).getDepartmentName(),
                    (ptid) -> positionService.findById(ptid).getChineseName());
            return new BaseRestResponse<>(true, "根据招聘需求id查找招聘需求成功!", demandListItemVo);
        }
    }

    /**
     * 根据招聘需求id逻辑删除该需求
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/myProfile/demand/delete/{id}", method = RequestMethod.GET)
    public BaseRestResponse<String> deleteDemand(@PathVariable("id") Long id) {
        BaseServiceResponse<Integer> deleteResult = demandService.deleteDemandLogically(id);
        if (!deleteResult.isSuccess()) {
            new BaseRestResponse<>(false, "下架该招聘需求失败!", null);
        }
        return new BaseRestResponse<>(true, "下架该招聘需求成功!", null);
    }
}

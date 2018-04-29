package org.wlyyy.itrs.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.*;
import org.wlyyy.itrs.dict.EnumFlowStatus;
import org.wlyyy.itrs.domain.*;
import org.wlyyy.itrs.request.ApplyFlowQuery;
import org.wlyyy.itrs.request.CandidateQuery;
import org.wlyyy.itrs.request.WorkFlowQuery;
import org.wlyyy.itrs.service.*;
import org.wlyyy.itrs.vo.ApplyFlowListItemVo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/myProfile/flow")
public class FlowController {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FlowController.class);

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private DemandService demandService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private UserService userService;

    @Autowired
    ApplyFlowService applyFlowService;

    @Autowired
    AuthenticationService authenticationService;

    final String NO_TASK = "-1";

    @RequestMapping("/deployFile")
    public BaseRestResponse<Deployment> deployWorkFlow_file(){
        // TODO
        return null;
    }

    /**
     * 根据classpath下的zip文件以及部署名称进行部署
     */
    @RequestMapping(value = "/deployZip", method = RequestMethod.GET)
    public BaseRestResponse<Deployment> deployWorkFlow_zip(String zipName, String deployName){
        BaseServiceResponse<Deployment> deploymentResult = workFlowService.deployWorkFlow_zip(zipName, deployName);
        if (deploymentResult.isSuccess()) {
            Deployment deployment = deploymentResult.getData();
            return new BaseRestResponse<>(true, "流程部署成功", deployment);
        } else {
            return new BaseRestResponse<>(false, "流程部署失败", null);
        }
    }

    /**
     * 员工给某一招聘需求推荐人才
     */
    @RequestMapping(value = "/recommendTalent")
    public BaseRestResponse<String> recommendTalent(final Long demandId, final Candidate candidate, final HttpServletRequest req) {
        // 1. 获取该招聘需求对应的procKey
        Demand demand = demandService.findById(demandId);
        String procKey = demand.getProcKey();
        String demandNo = demand.getDemandNo();
        Long hrId = demand.getPublisherId();

        // 2. 放入被推荐人信息到被推荐人信息表中
        // 先根据[被推荐人姓名+手机号]查询该被推荐人是否已存在被推荐人信息表中，若已存在，则查询其是否处于有效的招聘需求处理流程中
        // 若处于，则返回推荐失败；若不处于，则更新其在被推荐人信息表中的信息
        Long insertCandidateId = 0l;
        BaseServicePageableResponse<Candidate> recommendResult = candidateService.findByCondition(
                new BaseServicePageableRequest<>(1,1,
                        new CandidateQuery().setName(candidate.getName()).setPhoneNo(candidate.getPhoneNo())));
        if (recommendResult.getTotal() == 1) {
            // 该被推荐人已存在于被推荐人信息表中
            // 获取其id
            Candidate exitsCandidate = recommendResult.getDatas().get(0);
            Long exitsCandidateId = exitsCandidate.getId();
            insertCandidateId = exitsCandidateId;
            // 查询其是否处于有效的招聘需求处理流程中
            BaseServicePageableResponse<ApplyFlow> applyFlowResult = applyFlowService.findByCondition(new BaseServicePageableRequest<>(1, 1,
                    new ApplyFlowQuery().setCandidateId(exitsCandidateId).setDemandNo(demandNo).setFlowStatus(EnumFlowStatus.EXECUTION.getCode())));
            if (applyFlowResult.getTotal() == 1) {
                // 处于有效的招聘需求处理流程中，返回推荐失败
                return new BaseRestResponse<>(false, "该推荐人已在有效的招聘流程中，请勿重复推荐该人!", null);
            } else {
                // 不处于，进行推荐人信息更新
                candidate.setId(exitsCandidateId);
                candidateService.updateCandidate(candidate);
            }
        } else {
            // 插入新被推荐人信息，并获取id
            insertCandidateId = candidateService.insertCandidate(candidate).getData();
        }

        // 3. 放入数据到推荐表中
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();
        // 插入推荐表中
        Recommend recommend = new Recommend();
        recommend.setCandidateId(insertCandidateId);
        recommend.setUserId(userAgent.getId());
        recommendService.insertRecommend(recommend).getData();

        // 4. 放入数据到招聘流程信息表中
        ApplyFlow applyFlow = new ApplyFlow();
        applyFlow.setDemandNo(demandNo);
        applyFlow.setCandidateId(insertCandidateId);
        applyFlow.setUserId(userAgent.getId());
        applyFlow.setCurrentFlowNode("员工推荐");
        applyFlow.setCurrentDealer(userAgent.getId());
        applyFlow.setFlowStatus(EnumFlowStatus.EXECUTION.getCode());
        Long applyFlowId = applyFlowService.insertApplyFlow(applyFlow).getData();

        // 5. 启动流程实例，同时设置下一任务处理人是自己
        WorkFlow workFlow = new WorkFlow();
        workFlow.setProcKey(procKey);
        workFlow.setId(applyFlowId);
        workFlow.setRecommendId(userAgent.getId());
        String processInstanceId = workFlowService.startProcess(workFlow).getData().getProcessInstanceId();

        // 6. 根据任务id来完成任务，同时设置下一任务的处理人
        Task task = workFlowService.findRecommendTask(
                new WorkFlowQuery().setRecommendId(userAgent.getId()).setProcessInstanceId(processInstanceId).setTaskName("员工推荐"));
        workFlow.setTaskId(task.getId());
        workFlow.setPublisherId(hrId);
        BaseServiceResponse<String> completeResult = workFlowService.completeRecommendTask(workFlow);
        if (completeResult.isSuccess()) {
            // 更改当前流程节点和当前处理人
            applyFlow.setId(applyFlowId);
            applyFlow.setCurrentFlowNode("筛选简历");
            applyFlow.setCurrentDealer(hrId);
            applyFlowService.updateApplyFlow(applyFlow);
            return new BaseRestResponse<>(true, "员工推荐成功!", null);
        } else {
            applyFlow.setFlowStatus(EnumFlowStatus.EXCEPTION.getCode());
            applyFlow.setId(applyFlowId);
            // 异常，更新流程状态为“异常”
            applyFlowService.updateApplyFlow(applyFlow);
            return new BaseRestResponse<>(false, "员工推荐失败或出现异常!", null);
        }
    }

    /**
     * 根据招聘需求编号展示其下的展示层招聘流程信息表
     */
    @RequestMapping(value = "/listApplyFlow", method = RequestMethod.GET)
    BaseRestPageableResponse<ApplyFlowListItemVo> queryApplyFlowListByHrId(final Long demandId, final int pageNo, final int pageSize) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();
        Demand demand = demandService.findById(demandId);
        String demandNo = demand.getDemandNo();
        Long hrId = demand.getPublisherId();

        // 1. 根据招聘需求id找到其下的招聘流程列表
        BaseServicePageableRequest<ApplyFlowQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize,
                new ApplyFlowQuery().setDemandNo(demandNo));
        BaseServicePageableResponse<ApplyFlow> applyFlowResult = applyFlowService.findByCondition(request);
        List<ApplyFlow> applyFlowList = applyFlowResult.getDatas();

        // 2. 转化成的展示层招聘流程信息表
        // 在根据招聘流程id得到任务id和操作的过程中，若当前处理人不是该hr，则置任务id为-1，操作为空
        List<ApplyFlowListItemVo> datas = applyFlowList.stream().map(source -> ApplyFlowListItemVo.buildFromDomain(source,
                (cid) -> candidateService.findById(cid),
                (uid) -> userService.findById(uid).getUserName(),
                (aid) -> {
                    if (!hrId.equals(userAgent.getId())) {
                        return NO_TASK;
                    }
                    return workFlowService.findCurrentTaskByApplyId(aid).getData().getId();
                },
                (aid) -> {
                    if (!hrId.equals(userAgent.getId())) {
                        return new ArrayList<String>();
                    }
                    return workFlowService.findCurrentOutcomeListByApplyId(aid).getData();
                }))
                .collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询展示层招聘流程信息表成功!", datas,
                applyFlowResult.getPageNo(), applyFlowResult.getPageSize(), applyFlowResult.getTotal());
    }

    @RequestMapping(value = "/createUser")
    String createUser(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail("397055871@qq.com");
        user.setSex(1);
        user.setRealName("翁啦啦");
        user.setDepartmentId(1l);
        userService.createUser(user);
        return "Create user success!";
    }
}

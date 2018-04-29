package org.wlyyy.itrs.controller;

import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.dict.EnumFlowStatus;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.domain.Recommend;
import org.wlyyy.itrs.request.ApplyFlowQuery;
import org.wlyyy.itrs.request.CandidateQuery;
import org.wlyyy.itrs.request.RecommendQuery;
import org.wlyyy.itrs.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

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
    ApplyFlowService applyFlowService;

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

        // 2. 放入被推荐人信息到被推荐人信息表中
        // 先根据[被推荐人姓名+手机号]查询该被推荐人是否已存在被推荐人信息表中，若已存在，则查询其是否处于有效的招聘需求处理流程中
        // 若处于，则返回推荐失败；若不处于，则更新其在被推荐人信息表中的信息
        BaseServicePageableResponse<Candidate> recommendResult = candidateService.findByCondition(
                new BaseServicePageableRequest<>(1,1,
                        new CandidateQuery().setName(candidate.getName()).setPhoneNo(candidate.getPhoneNo())));
        if (recommendResult.getTotal() == 1) {
            // 该被推荐人已存在于被推荐人信息表中
            // 获取其id
            Candidate exitsCandidate = recommendResult.getDatas().get(0);
            Long exitsCandidateId = exitsCandidate.getId();
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
            // 插入新被推荐人信息
            candidateService.insertCandidate(candidate);
            Long id = candidate.getId();
        }

        // 3. 放入数据到推荐表中



        return null;
    }
}

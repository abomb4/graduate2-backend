package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.dict.EnumFlowStatus;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.domain.Demand;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;

/**
 * 展示层招聘流程信息表，这里展示：
 * (招聘流程id 需求id 被推荐人id 推荐人id 需求编号 任务id) 被推荐人姓名 被推荐人性别 期望工作地 推荐人姓名 当前流程节点 当前处理人 流程状态 操作
 *
 * @author wly
 */
public class ApplyFlowListItemVo {

    /**
     * 从Domain对象构建视图对象
     * 需要传入两个Lambda表达式来获取被推荐人的信息和推荐人姓名
     *
     * @param source                原始Domain对象
     * @param getCandidateNameById  根据被推荐人id获取被推荐人信息
     * @param getUserNameById       根据用户id获取用户姓名
     * @param getTaskIdById         根据招聘流程id得到对应的任务id（若无需操作，则id为-1）
     * @param getOutcomeListById    根据招聘流程id得到当前流程节点的处理连线
     * @param getDemandByNo         根据需求No得到需求对象
     * @param getPositionTypeCnName 根据职位类别id得到职位类别中文名
     * @return 视图VO对象
     */
    public static ApplyFlowListItemVo buildFromDomain(
            ApplyFlow source,
            Function<Long, Candidate> getCandidateNameById,         // 根据被推荐人id得到被推荐人信息
            Function<Long, String> getUserNameById,                 // 根据用户id得到用户姓名
            Function<Long, String> getTaskIdById,                   // 根据招聘流程id得到对应的任务id（若无需操作，则id为-1）
            Function<Long, String> getTaskNameById,                 // 根据招聘流程id得到对应的任务名称
            Function<Long, List<String>> getOutcomeListById,        // 根据招聘流程id得到当前流程节点的处理连线
            Function<String, Demand> getDemandByNo,                 // 根据需求No得到需求对象
            Function<Long, String> getPositionTypeCnName            // 根据职位类别id得到职位类别中文名
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final Demand demand = getDemandByNo.apply(source.getDemandNo());
        final Long demandId = demand.getId();
        final String demandNo = source.getDemandNo();
        final String jobName = demand.getJobName();
        final Long positionType = demand.getPositionType();
        final String positionTypeCnName = getPositionTypeCnName.apply(positionType);
        final Long publisherId = demand.getPublisherId();
        final String taskId = getTaskIdById.apply(source.getId());
        final String taskName = getTaskNameById.apply(source.getId());
        final Long candidateId = source.getCandidateId();
        final Candidate candidate = getCandidateNameById.apply(source.getCandidateId());
        final String candidateName = candidate.getName();
        final Integer candidateSex = candidate.getSex();
        final String hopeWorkingPlace = candidate.getWorkingPlace();
        final Long recommendId = source.getUserId();
        final String recommendName = getUserNameById.apply(source.getUserId());
        final String currentFlowNode = source.getCurrentFlowNode();
        final String currentDealer = getUserNameById.apply(source.getCurrentDealer());
        final String currentResult = source.getCurrentResult();
        final Integer flowStatus = source.getFlowStatus();
        final String flowStatusName = EnumFlowStatus.getDescByCode(source.getFlowStatus());
        final List<String> operate = getOutcomeListById.apply(source.getId());
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new ApplyFlowListItemVo(
                id, demandId, demandNo, jobName, positionType, positionTypeCnName, publisherId,
                taskId, taskName, candidateId, candidateName, candidateSex,
                hopeWorkingPlace, recommendId, recommendName, currentFlowNode,
                currentDealer, currentResult, flowStatus, flowStatusName,
                gmtCreate, gmtModify, operate
        );
    }

    public ApplyFlowListItemVo() {
    }

    public ApplyFlowListItemVo(
            Long id, Long demandId, String demandNo, String jobName, Long positionType, String positionTypeCnName,
            Long publisherId, String taskId, String taskName, Long candidateId, String candidateName,
            Integer candidateSex, String hopeWorkingPlace, Long recommendId, String recommendName,
            String currentFlowNode, String currentDealer, String currentResult, Integer flowStatus,
            String flowStatusName, String gmtCreate, String gmtModify, List<String> operate
    ) {
        this.id = id;
        this.demandId = demandId;
        this.demandNo = demandNo;
        this.jobName = jobName;
        this.positionType = positionType;
        this.positionTypeCnName = positionTypeCnName;
        this.publisherId = publisherId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateSex = candidateSex;
        this.hopeWorkingPlace = hopeWorkingPlace;
        this.recommendId = recommendId;
        this.recommendName = recommendName;
        this.currentFlowNode = currentFlowNode;
        this.currentDealer = currentDealer;
        this.currentResult = currentResult;
        this.flowStatus = flowStatus;
        this.flowStatusName = flowStatusName;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
        this.operate = operate;
    }

    private Long id;
    private Long demandId;
    private String demandNo;
    private String jobName;
    private Long positionType;
    private String positionTypeCnName;
    private Long publisherId;
    private String taskId;
    private String taskName;
    private Long candidateId;
    private String candidateName;
    private Integer candidateSex;
    private String hopeWorkingPlace;
    private Long recommendId;
    private String recommendName;
    private String currentFlowNode;
    private String currentDealer;
    private String currentResult;
    private Integer flowStatus;
    private String flowStatusName;
    private String gmtCreate;
    private String gmtModify;
    private List<String> operate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getPositionType() {
        return positionType;
    }

    public void setPositionType(Long positionType) {
        this.positionType = positionType;
    }

    public String getPositionTypeCnName() {
        return positionTypeCnName;
    }

    public void setPositionTypeCnName(String positionTypeCnName) {
        this.positionTypeCnName = positionTypeCnName;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Integer getCandidateSex() {
        return candidateSex;
    }

    public void setCandidateSex(Integer candidateSex) {
        this.candidateSex = candidateSex;
    }

    public String getHopeWorkingPlace() {
        return hopeWorkingPlace;
    }

    public void setHopeWorkingPlace(String hopeWorkingPlace) {
        this.hopeWorkingPlace = hopeWorkingPlace;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getCurrentFlowNode() {
        return currentFlowNode;
    }

    public void setCurrentFlowNode(String currentFlowNode) {
        this.currentFlowNode = currentFlowNode;
    }

    public String getCurrentDealer() {
        return currentDealer;
    }

    public void setCurrentDealer(String currentDealer) {
        this.currentDealer = currentDealer;
    }

    public String getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowStatusName() {
        return flowStatusName;
    }

    public void setFlowStatusName(String flowStatusName) {
        this.flowStatusName = flowStatusName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(String gmtModify) {
        this.gmtModify = gmtModify;
    }

    public List<String> getOperate() {
        return operate;
    }

    public void setOperate(List<String> operate) {
        this.operate = operate;
    }
}

package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.dict.EnumFlowStatus;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.domain.Candidate;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;

/**
 * 展示层招聘流程信息表，这里展示：
 * (招聘流程id 需求编号 任务id) 被推荐人姓名 被推荐人性别 期望工作地 推荐人姓名 当前流程节点 当前处理人 流程状态 操作
 *
 * @author wly
 */
public class ApplyFlowListItemVo {

    /**
     * 从Domain对象构建视图对象
     * 需要传入两个Lambda表达式来获取被推荐人的信息和推荐人姓名
     *
     * @param source                 原始Domain对象
     * @param getCandidateNameById   根据被推荐人id获取被推荐人信息
     * @param getUserNameById        根据用户id获取用户姓名
     * @param getTaskIdById          根据招聘流程id得到对应的任务id（若无需操作，则id为-1）
     * @param getOutcomeListById     根据招聘流程id得到当前流程节点的处理连线
     * @return                       视图VO对象
     */
    public static ApplyFlowListItemVo buildFromDomain(
            ApplyFlow source,
            Function<Long, Candidate> getCandidateNameById,         // 根据被推荐人id得到被推荐人信息
            Function<Long, String> getUserNameById,                 // 根据用户id得到用户姓名
            Function<Long, String> getTaskIdById,                   // 根据招聘流程id得到对应的任务id（若无需操作，则id为-1）
            Function<Long, List<String>> getOutcomeListById         // 根据招聘流程id得到当前流程节点的处理连线
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final String demandNo = source.getDemandNo();
        final String taskId = getTaskIdById.apply(source.getId());
        final String candidateName = getCandidateNameById.apply(source.getCandidateId()).getName();
        final Integer candidateSex = getCandidateNameById.apply(source.getCandidateId()).getSex();
        final String hopeWorkingPlace = getCandidateNameById.apply(source.getCandidateId()).getWorkingPlace();
        final String recommendName = getUserNameById.apply(source.getUserId());
        final String currentFlowNode = source.getCurrentFlowNode();
        final String currentDealer = getUserNameById.apply(source.getCurrentDealer());
        final Integer flowStatus = source.getFlowStatus();
        final String flowStatusName = EnumFlowStatus.getDescByCode(source.getFlowStatus());
        final List<String> operate = getOutcomeListById.apply(source.getId());
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new  ApplyFlowListItemVo(
                id, demandNo, taskId, candidateName,
                candidateSex, hopeWorkingPlace, recommendName,
                currentFlowNode, currentDealer, flowStatus, flowStatusName,
                gmtCreate, gmtModify, operate
        );
    }

    public ApplyFlowListItemVo() {
    }

    public ApplyFlowListItemVo(
            Long id, String demandNo, String taskId, String candidateName,
            Integer candidateSex, String hopeWorkingPlace, String recommendName,
            String currentFlowNode, String currentDealer, Integer flowStatus, String flowStatusName,
            String gmtCreate, String gmtModify, List<String> operate
    ) {
        this.id = id;
        this.demandNo = demandNo;
        this.taskId = taskId;
        this.candidateName = candidateName;
        this.candidateSex = candidateSex;
        this.hopeWorkingPlace = hopeWorkingPlace;
        this.recommendName = recommendName;
        this.currentFlowNode = currentFlowNode;
        this.currentDealer = currentDealer;
        this.flowStatus = flowStatus;
        this.flowStatusName = flowStatusName;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
        this.operate = operate;
    }

    private Long id;
    private String demandNo;
    private String taskId;
    private String candidateName;
    private Integer candidateSex;
    private String hopeWorkingPlace;
    private String recommendName;
    private String currentFlowNode;
    private String currentDealer;
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

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

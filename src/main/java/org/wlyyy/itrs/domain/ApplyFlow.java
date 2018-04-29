package org.wlyyy.itrs.domain;

import java.util.Date;

/**
 * 招聘流程信息
 */
public class ApplyFlow {

    /**
     * 招聘流程id，同时与activiti中的BussinessKey作为业务关联
     */
    private Long id;

    /**
     * 需求编号
     */
    private String demandNo;

    /**
     * 被推荐人id
     */
    private Long candidateId;

    /**
     * 推荐人id
     */
    private Long userId;

    /**
     * 当前流程节点
     */
    private String currentFlowNode;

    /**
     * 当前处理人
     */
    private Long currentDealer;

    /**
     * 流程状态
     */
    private Integer flowStatus;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

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

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrentFlowNode() {
        return currentFlowNode;
    }

    public void setCurrentFlowNode(String currentFlowNode) {
        this.currentFlowNode = currentFlowNode;
    }

    public Long getCurrentDealer() {
        return currentDealer;
    }

    public void setCurrentDealer(Long currentDealer) {
        this.currentDealer = currentDealer;
    }

    public Integer getFlowStatus() { return flowStatus; }

    public void setFlowStatus(Integer flowStatus) { this.flowStatus = flowStatus; }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}

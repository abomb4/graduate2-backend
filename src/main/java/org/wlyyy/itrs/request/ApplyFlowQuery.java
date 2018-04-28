package org.wlyyy.itrs.request;

import java.util.Date;

public class ApplyFlowQuery {

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
    private int flowStatus;

    private Date gmtCreateStart;

    private Date gmtCreateEnd;

    private Date gmtModifyStart;

    private Date gmtModifyEnd;

    public Long getId() {
        return id;
    }

    public ApplyFlowQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public ApplyFlowQuery setDemandNo(String demandNo) {
        this.demandNo = demandNo;
        return this;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public ApplyFlowQuery setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ApplyFlowQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getCurrentFlowNode() {
        return currentFlowNode;
    }

    public ApplyFlowQuery setCurrentFlowNode(String currentFlowNode) {
        this.currentFlowNode = currentFlowNode;
        return this;
    }

    public Long getCurrentDealer() {
        return currentDealer;
    }

    public ApplyFlowQuery setCurrentDealer(Long currentDealer) {
        this.currentDealer = currentDealer;
        return this;
    }

    public int getFlowStatus() {
        return flowStatus;
    }

    public ApplyFlowQuery setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public ApplyFlowQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public ApplyFlowQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public ApplyFlowQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public ApplyFlowQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }
}

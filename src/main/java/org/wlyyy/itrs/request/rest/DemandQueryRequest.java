package org.wlyyy.itrs.request.rest;

import org.springframework.data.domain.Sort;
import org.wlyyy.itrs.request.DemandQuery;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

/**
 * 招聘需求查询对象。
 */
public class DemandQueryRequest {

    private Long id;
    private String demandNo;
    private Long publisherId;
    private Long positionType;
    private String jobName;
    private Long departmentId;
    private String hrName;
    private Long totalStart;
    private Long totalEnd;
    private String workingPlace;
    private String degreeRequest;
    private Integer status;
    private String procKey;
    private String memo;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public DemandQuery buildDemandQuery(Function<Long, Collection<Long>> dealParentPositionType) {
        return new DemandQuery(
                id,
                demandNo,
                publisherId,
                dealParentPositionType.apply(positionType),
                jobName,
                departmentId,
                hrName,
                totalStart,
                totalEnd,
                workingPlace,
                degreeRequest,
                status,
                procKey,
                memo,
                gmtCreateStart,
                gmtCreateEnd,
                gmtModifyStart,
                gmtModifyEnd,
                sort
        );
    }

    public Long getId() {
        return id;
    }

    public DemandQueryRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public DemandQueryRequest setDemandNo(String demandNo) {
        this.demandNo = demandNo;
        return this;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public DemandQueryRequest setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
        return this;
    }

    public Long getPositionType() {
        return positionType;
    }

    public DemandQueryRequest setPositionType(Long positionType) {
        this.positionType = positionType;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public DemandQueryRequest setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public DemandQueryRequest setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public String getHrName() {
        return hrName;
    }

    public DemandQueryRequest setHrName(String hrName) {
        this.hrName = hrName;
        return this;
    }

    public Long getTotalStart() {
        return totalStart;
    }

    public DemandQueryRequest setTotalStart(Long totalStart) {
        this.totalStart = totalStart;
        return this;
    }

    public Long getTotalEnd() {
        return totalEnd;
    }

    public DemandQueryRequest setTotalEnd(Long totalEnd) {
        this.totalEnd = totalEnd;
        return this;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public DemandQueryRequest setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
        return this;
    }

    public String getDegreeRequest() {
        return degreeRequest;
    }

    public DemandQueryRequest setDegreeRequest(String degreeRequest) {
        this.degreeRequest = degreeRequest;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DemandQueryRequest setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getProcKey() {
        return procKey;
    }

    public DemandQueryRequest setProcKey(String procKey) {
        this.procKey = procKey;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public DemandQueryRequest setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public DemandQueryRequest setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public DemandQueryRequest setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public DemandQueryRequest setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public DemandQueryRequest setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public DemandQueryRequest setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}

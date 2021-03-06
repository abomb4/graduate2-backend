package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Date;

/**
 * 需求查询对象。
 */
public class DemandQuery {
    private Long id;
    private String demandNo;
    private Long publisherId;
    private Collection<Long> positionType;
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
    private java.util.Date gmtCreateStart;
    private java.util.Date gmtCreateEnd;
    private java.util.Date gmtModifyStart;
    private java.util.Date gmtModifyEnd;
    private Sort sort;

    public DemandQuery() {
    }

    public DemandQuery(
            Long id,
            String demandNo,
            Long publisherId,
            Collection<Long> positionType,
            String jobName,
            Long departmentId,
            String hrName,
            Long totalStart,
            Long totalEnd,
            String workingPlace,
            String degreeRequest,
            Integer status,
            String procKey,
            String memo,
            Date gmtCreateStart,
            Date gmtCreateEnd,
            Date gmtModifyStart,
            Date gmtModifyEnd,
            Sort sort) {
        this.id = id;
        this.demandNo = demandNo;
        this.publisherId = publisherId;
        this.positionType = positionType;
        this.jobName = jobName;
        this.departmentId = departmentId;
        this.hrName = hrName;
        this.totalStart = totalStart;
        this.totalEnd = totalEnd;
        this.workingPlace = workingPlace;
        this.degreeRequest = degreeRequest;
        this.status = status;
        this.procKey = procKey;
        this.memo = memo;
        this.gmtCreateStart = gmtCreateStart;
        this.gmtCreateEnd = gmtCreateEnd;
        this.gmtModifyStart = gmtModifyStart;
        this.gmtModifyEnd = gmtModifyEnd;
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public DemandQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public DemandQuery setDemandNo(String demandNo) {
        this.demandNo = demandNo;
        return this;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public DemandQuery setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
        return this;
    }

    public Collection<Long> getPositionType() {
        return positionType;
    }

    public DemandQuery setPositionType(Collection<Long> positionType) {
        this.positionType = positionType;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public DemandQuery setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public DemandQuery setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public String getHrName() {
        return hrName;
    }

    public DemandQuery setHrName(String hrName) {
        this.hrName = hrName;
        return this;
    }

    public Long getTotalStart() {
        return totalStart;
    }

    public DemandQuery setTotalStart(Long totalStart) {
        this.totalStart = totalStart;
        return this;
    }

    public Long getTotalEnd() {
        return totalEnd;
    }

    public DemandQuery setTotalEnd(Long totalEnd) {
        this.totalEnd = totalEnd;
        return this;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public DemandQuery setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
        return this;
    }

    public String getDegreeRequest() {
        return degreeRequest;
    }

    public DemandQuery setDegreeRequest(String degreeRequest) {
        this.degreeRequest = degreeRequest;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DemandQuery setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getProcKey() {
        return procKey;
    }

    public DemandQuery setProcKey(String procKey) {
        this.procKey = procKey;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public DemandQuery setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public DemandQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public DemandQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public DemandQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public DemandQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public DemandQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}

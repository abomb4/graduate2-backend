package org.wlyyy.itrs.domain;

import java.util.Date;

/**
 * 需求对象。
 *
 * @author wly
 */
public class Demand {
    private Long id;
    private String demandNo;
    private Long publisherId;
    private String positionType;
    private String position;
    private Long departmentId;
    private String hrName;
    private Long total;
    private String workingPlace;
    private String degreeRequest;
    private Integer status;
    private String memo;
    private java.util.Date gmtCreate;
    private java.util.Date gmtModify;

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

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getDegreeRequest() {
        return degreeRequest;
    }

    public void setDegreeRequest(String degreeRequest) {
        this.degreeRequest = degreeRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

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

    @Override
    public String toString() {
        return "Demand{" +
                "id=" + id +
                ", demandNo='" + demandNo + '\'' +
                ", publisherId=" + publisherId +
                ", positionType='" + positionType + '\'' +
                ", position='" + position + '\'' +
                ", departmentId=" + departmentId +
                ", hrName='" + hrName + '\'' +
                ", total=" + total +
                ", workingPlace='" + workingPlace + '\'' +
                ", degreeRequest='" + degreeRequest + '\'' +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                '}';
    }
}

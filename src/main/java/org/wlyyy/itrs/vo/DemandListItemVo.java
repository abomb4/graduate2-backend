package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.dict.EnumDemandStatus;
import org.wlyyy.itrs.domain.Demand;

import java.text.SimpleDateFormat;
import java.util.function.Function;

/**
 * 展示层需求列表对象
 *
 * @author wly
 */
public class DemandListItemVo {

    /**
     * 从Domain对象构建视图对象。
     * 需要传入两个Lambda表达式来获取部门名和发布者名。
     *
     * @param source                原始Domain对象
     * @param getDepartmentNameById 获取部门名称的方法
     * @param getPublisherNameById  获取发布用户的姓名的方法
     * @param getPositionCnNameById 获取职务类别中文名
     * @return 视图VO对象
     */
    public static DemandListItemVo buildFromDomain(
            Demand source,
            Function<Long, String> getPublisherNameById,
            Function<Long, String> getDepartmentNameById,
            Function<Long, String> getPositionCnNameById
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        final Long id = source.getId();
        final String demandNo = source.getDemandNo();
        final Long publisherId = source.getPublisherId();
        final String publisherName = getPublisherNameById.apply(source.getPublisherId());
        final Long positionType = source.getPositionType();
        final String positionTypeCnName = getPositionCnNameById.apply(source.getPositionType());
        final String position = source.getJobName();
        final Long departmentId = source.getDepartmentId();
        final String departmentName = getDepartmentNameById.apply(source.getDepartmentId());
        final String hrName = source.getHrName();
        final Long total = source.getTotal();
        final String workingPlace = source.getWorkingPlace();
        final String degreeRequest = source.getDegreeRequest();
        final int status = source.getStatus();
        final String statusName = EnumDemandStatus.getDescByCode(source.getStatus());
        final String memo = source.getMemo();
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new DemandListItemVo(
                id,
                demandNo,
                publisherId,
                publisherName,
                positionType,
                positionTypeCnName,
                position,
                departmentId,
                departmentName,
                hrName,
                total,
                workingPlace,
                degreeRequest,
                status,
                statusName,
                memo,
                gmtCreate,
                gmtModify
        );

    }

    public DemandListItemVo() {
    }

    public DemandListItemVo(
            Long id, String demandNo, Long publisherId,
            String publisherName, Long positionType, String positionTypeCnName,
            String jobName, Long departmentId, String departmentName, String hrName,
            Long total, String workingPlace, String degreeRequest,
            Integer status, String statusName, String memo, String gmtCreate,
            String gmtModify
    ) {
        this.id = id;
        this.demandNo = demandNo;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.positionType = positionType;
        this.positionTypeCnName = positionTypeCnName;
        this.jobName = jobName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.hrName = hrName;
        this.total = total;
        this.workingPlace = workingPlace;
        this.degreeRequest = degreeRequest;
        this.status = status;
        this.statusName = statusName;
        this.memo = memo;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    private Long id;
    private String demandNo;
    private Long publisherId;
    private String publisherName;
    private Long positionType;
    private String positionTypeCnName;
    private String jobName;
    private Long departmentId;
    private String departmentName;
    private String hrName;
    private Long total;
    private String workingPlace;
    private String degreeRequest;
    private Integer status;
    private String statusName;
    private String memo;
    private String gmtCreate;
    private String gmtModify;

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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
}

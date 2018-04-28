package org.wlyyy.itrs.domain;

import java.io.File;
import java.util.List;

/**
 * 工作流查询对象
 */
public class WorkFlow {

    /************以下这部分属性与工作流本身相关************/
    /**
     * 流程定义部署文件
     */
    private File file;

    /**
     * 流程定义部署名称
     */
    private String filename;

    /**
     * 部署对象id
     */
    private String deploymentId;

    /**
     * 流程定义的key
     */
    private String procKey;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述，便于分辨这个任务是展示在hr端还是面试官端
     * 因为一个用户同时可能是hr or 面试官
     */
    private String taskDesc;

    /**
     * 下一任务的完成人
     */
    private List<Long> nextUserId;

    /**
     * 连线名称（通过or不通过等）
     */
    private String outcome;

    /**
     * 备注
     */
    private String comment;


    /************以下这部分属性与业务本身相关************/
    /**
     * 招聘流程信息id
     */
    private Long id;

    /**
     * 被推荐人id
     */
    private Long candidateId;

    /**
     * 推荐人id
     */
    private Long recommendId;

    /**
     * 招聘需求发布人id
     */
    private Long publisherId;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public List<Long> getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(List<Long> nextUserId) {
        this.nextUserId = nextUserId;
    }
}

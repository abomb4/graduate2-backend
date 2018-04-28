package org.wlyyy.itrs.request;

import java.io.File;

/**
 * 工作流查询对象
 */
public class WorkFlowQuery {

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

    public WorkFlowQuery setFile(File file) {
        this.file = file;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public WorkFlowQuery setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public WorkFlowQuery setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public WorkFlowQuery setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getOutcome() {
        return outcome;
    }

    public WorkFlowQuery setOutcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public WorkFlowQuery setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Long getId() {
        return id;
    }

    public WorkFlowQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getprocKey() {
        return procKey;
    }

    public WorkFlowQuery setProcKey(String procKey) {
        this.procKey = procKey;
        return this;
    }

    public String getProcKey() {
        return procKey;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public WorkFlowQuery setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public WorkFlowQuery setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
        return this;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public WorkFlowQuery setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public WorkFlowQuery setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getTaskName() {
        return taskName;
    }

    public WorkFlowQuery setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public WorkFlowQuery setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
        return this;
    }
}

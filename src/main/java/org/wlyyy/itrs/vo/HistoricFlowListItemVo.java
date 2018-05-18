package org.wlyyy.itrs.vo;

import org.activiti.engine.history.HistoricTaskInstance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * 展示层历史招聘流程信息表，这里展示：
 * (任务id 招聘流程id 招聘需求No) 处理节点 处理结果 处理开始时间 处理结束时间
 *
 * @author wly
 */
public class HistoricFlowListItemVo {

    /**
     *
     * @param source                        原始Domain对象
     * @param findVarByTaskId               根据任务id查找流程变量(当前流程结果 result)值
     * @param findApplyFlowIdByProcId       根据流程实例id获取招聘流程id
     * @param findDemandNoByApplyFlowId     根据招聘需求id获取招聘需求No
     * @return 展示层历史招聘流程对象
     */
    public static HistoricFlowListItemVo buildFromDomain(
            HistoricTaskInstance source,
            Function<String, String> findVarByTaskId,           // 根据任务id查找流程变量(当前流程结果 result)值
            Function<String, Long> findApplyFlowIdByProcId,     // 根据流程实例id获取招聘流程id
            Function<Long, String> findDemandNoByApplyFlowId    // 根据招聘需求id获取招聘需求No
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String taskId = source.getId();
        final Long applyFlowId = findApplyFlowIdByProcId.apply(source.getProcessInstanceId());
        final String demandNo = findDemandNoByApplyFlowId.apply(applyFlowId);
        final String node = source.getName();
        final String result = findVarByTaskId.apply(source.getId());
        final String startTime = formatter.format(source.getStartTime());
        final Date endTimeDate = source.getEndTime();
        final String endTime;
        if (endTimeDate != null) {
            endTime = formatter.format(endTimeDate);
        } else {
            endTime = "未处理完";
        }

        return  new HistoricFlowListItemVo(taskId, applyFlowId, demandNo, node, result, startTime, endTime);
    }

    public HistoricFlowListItemVo() {
    }

    private HistoricFlowListItemVo(
            String taskId, Long applyFlowId, String demandNo, String node, String result, String startTime, String endTime
    ) {
        this.taskId = taskId;
        this.applyFlowId = applyFlowId;
        this.demandNo = demandNo;
        this.node = node;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String taskId;
    private Long applyFlowId;
    private String demandNo;
    private String node;
    private String result;
    private String startTime;
    private String endTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getApplyFlowId() {
        return applyFlowId;
    }

    public void setApplyFlowId(Long applyFlowId) {
        this.applyFlowId = applyFlowId;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

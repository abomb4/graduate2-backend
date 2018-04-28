package org.wlyyy.itrs.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.engine.task.TaskQuery;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.dict.EnumTableName;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.domain.WorkFlow;
import org.wlyyy.itrs.request.WorkFlowQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class WorkFlowServiceImpl implements  WorkFlowService{

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    final String NO_TASK = "-1";

    @Override
    public BaseServiceResponse<Deployment> deployWorkFlow_file(File file, String deployName) {
        try {
            // 将File类型的文件转化成ZipInputStream流
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            Deployment deployment = repositoryService.createDeployment()
                    .name(deployName)
                    .addZipInputStream(zipInputStream)
                    .deploy();
            return new BaseServiceResponse<>(true, "Deploy success", deployment, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseServiceResponse<>(false, "Deploy fail", null, e);
        }
    }

    @Override
    public BaseServiceResponse<Deployment> deployWorkFlow_zip(String zipName, String deployName) {
        try {
            // 读取classpath下的zip文件
            String pathName = "processes/" + zipName;
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(pathName);
            ZipInputStream zipInputStream = new ZipInputStream(in);
            Deployment deployment = repositoryService.createDeployment()
                    .name(deployName)
                    .addZipInputStream(zipInputStream)
                    .deploy();
            return new BaseServiceResponse<>(true, "Deploy success", deployment, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseServiceResponse<>(false, "Deploy fail", null, null);
        }
    }

    @Override
    public BaseServicePageableResponse<Deployment> findAllDeploy(BaseServicePageableRequest<WorkFlowQuery> request) {
        int firstResult = (request.getPageNo() - 1) * request.getPageSize();    // 查询起始项，从0开始
        int maxResults = request.getPageSize();    // 查询数量
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery()
                .orderByDeploymenTime().desc()
                .listPage(firstResult, maxResults);

        final long count;
        if (deploymentList.size() < request.getPageSize()) {
            count = deploymentList.size();
        } else {
            count = request.getPageSize();
        }

        return new BaseServicePageableResponse<>(
                true, "DeploymentList query success!", deploymentList,
                request.getPageNo(), request.getPageSize(),  count
                );
    }

    @Override
    public BaseServiceResponse<ProcessInstance> startProcess(WorkFlow workFlow) {
        String procKey = workFlow.getProcKey();
        Long busID = workFlow.getId();
        // 流程实例对应的BussinessKey，这里规定由 {招聘流程信息表的表名.id} 构成
        String bussinessKey = EnumTableName.APPLY_FLOW.getCode() + "." + busID;
        Long recommendId = workFlow.getRecommendId();
        Map<String, Object> variables = new HashMap<String,Object>();
        variables.put("userId", recommendId.toString());

        // 根据key值启动流程实例，当key值相同时，会按照最新版部署的流程进行启动
        // 同时设置BussinessKey（将activiti自带的表与业务表相关联）和下一任务的处理人（自己）
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(procKey, bussinessKey, variables);

        return new BaseServiceResponse(true, "Start process instance suceess!", pi, null);
    }

    @Override
    public BaseServicePageableResponse<Task> findTaskByAssigneeAndDesc(BaseServicePageableRequest<WorkFlowQuery> request, UserAgent user) {
        int firstResult = (request.getPageNo() - 1) * request.getPageSize();    // 查询起始项，从0开始
        int maxResults = request.getPageSize();    // 查询数量
        Long userId = user.getId();
        WorkFlowQuery query = request.getData();

        TaskQuery taskQuery = taskService.createTaskQuery()
                .orderByTaskCreateTime()
                .taskAssignee(userId.toString());
        if (StringUtils.isBlank(query.getTaskDesc())) {
            // 查询展现在hr端或面试官端的任务
            taskQuery.taskDescriptionLike('%' + query.getTaskDesc() + '%');
        }

        List<Task> taskList = taskQuery
                .listPage(firstResult, maxResults);

        final long count;
        if (taskList.size() < request.getPageSize()) {
            count = taskList.size();
        } else {
            count = request.getPageSize();
        }

        return new BaseServicePageableResponse<>(
                true, "DeploymentList query success!", taskList,
                request.getPageNo(), request.getPageSize(),  count
        );
    }

    @Override
    public BaseServicePageableResponse<Task> findHistoricTaskByAssigneeAndDesc(BaseServicePageableRequest<WorkFlowQuery> request, UserAgent user) {
        int firstResult = (request.getPageNo() - 1) * request.getPageSize();    // 查询起始项，从0开始
        int maxResults = request.getPageSize();    // 查询数量
        Long userId = user.getId();
        WorkFlowQuery query = request.getData();

        TaskInfoQuery hisTaskQuery = historyService.createHistoricTaskInstanceQuery()
                .orderByTaskCreateTime()
                .taskAssignee(userId.toString());
        if (StringUtils.isBlank(query.getTaskDesc())) {
            // 查询展现在hr端或面试官端的任务
            hisTaskQuery.taskDescriptionLike('%' + query.getTaskDesc() + '%');
        }

        List<Task> taskList = hisTaskQuery
                .listPage(firstResult, maxResults);

        final long count;
        if (taskList.size() < request.getPageSize()) {
            count = taskList.size();
        } else {
            count = request.getPageSize();
        }

        return new BaseServicePageableResponse<>(
                true, "DeploymentList query success!", taskList,
                request.getPageNo(), request.getPageSize(),  count
        );
    }

    @Override
    public BaseServiceResponse<Task> findCurrentTaskByApplyId(Long id) {
        String bussinessKey = EnumTableName.APPLY_FLOW.getCode() + id;
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(bussinessKey)
                .singleResult();
        return new BaseServiceResponse<>(true, "Find task by applyId success!", task, null);
    }

    @Override
    public BaseServiceResponse<String> completeTaskByTaskId(WorkFlow workFlow) {
        try {
            String taskId = workFlow.getTaskId();
            List<Long> nextUserId = workFlow.getNextUserId();
            String outcome = workFlow.getOutcome();

            if (taskId.equals(NO_TASK)) {
                return new BaseServiceResponse<>(false, "No task to complete!", null, null);
            }

            if (nextUserId == null && nextUserId.size() == 0) {
                // 没有下一步任务的完成人，即流程即将结束
                if (StringUtils.isNotBlank(outcome)) {
                    Map<String, Object> variables = new HashMap<String,Object>();
                    variables.put("outcome", outcome);
                    taskService.complete(taskId, variables);
                } else {
                    taskService.complete(taskId);
                }
                return new BaseServiceResponse<>(true, "Complete last task success!", null, null);
            } else {
                // 设置流程变量，指定任务结果（outcome）和下一任务的完成人（userId）
                Map<String, Object> variables = new HashMap<String,Object>();
                if (StringUtils.isNotBlank(outcome)) {
                    variables.put("outcome", outcome);
                }
                if (nextUserId.size() == 1) {
                    variables.put("userId", nextUserId.get(0).toString());
                } else {
                    variables.put("userId", nextUserId);
                }

                taskService.complete(taskId, variables);
                return new BaseServiceResponse<>(true, "Complete task success!", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseServiceResponse<>(false, "Complete task fail", null, e);
        }

    }

    @Override
    public BaseServiceResponse<Boolean> isFinishByApplyId(Long id) {
        String bussinessKey = EnumTableName.APPLY_FLOW.getCode() + id;
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(bussinessKey)
                .singleResult();
        if (task != null) {
            // 仍可以查到该招聘流程的当前流程节点
            return new BaseServiceResponse<>(true, "The apply is not finished!", false, null);
        } else {
            // 无法查到该招聘流程的当前流程节点
            return new BaseServiceResponse<>(true, "The apply is finished!", true, null);
        }
    }

    @Override
    public BaseServiceResponse<List<String>> findCurrentOutcomeListByApplyId(Long id) {
        // 存放连线的名称集合
        List<String> list = new ArrayList<String>();
        String bussinessKey = EnumTableName.APPLY_FLOW.getCode() + id;
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(bussinessKey)
                .singleResult();
        // 获取当前流程节点id
        String currentNodeId = task.getTaskDefinitionKey();
        // 获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();

        // 获取ProcessDefinitionEntity对象，对应xml文件的信息
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        // 获取xml文件中的当前流程节点
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(currentNodeId);
        // 获取当前流程节点的处理连线
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if(pvmList != null && pvmList.size() > 0){
            for (PvmTransition pvm : pvmList){
                String name = (String) pvm.getProperty("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(name);
                }
                else{
                    list.add("默认操作");
                }
            }
        }

        return new BaseServiceResponse<>(true, "Find current outcome success", list, null);
    }

    @Override
    public Task findRecommendTask(WorkFlowQuery query) {
        Long recommendId = query.getRecommendId();
        String processInstanceId = query.getProcessInstanceId();
        String taskName = query.getTaskName();

        Task recommendTask = taskService.createTaskQuery()
                .taskAssignee(recommendId.toString())
                .processInstanceId(processInstanceId)
                .taskName(taskName)
                .singleResult();

        return recommendTask;
    }

    @Override
    public void completeRecommendTask(WorkFlow workFlow) {
        String taskId = workFlow.getTaskId();
        Long publisherId = workFlow.getPublisherId();
        Map<String, Object> variables = new HashMap<String,Object>();
        variables.put("userId", publisherId.toString());
        // variables.put("outcome", workFlow.getOutcome());
        variables.put("outcome", "推荐");

        // 完成任务的同时，设置下一任务的处理人（发布的hr）和任务结果
        taskService.complete(taskId, variables);
    }
}
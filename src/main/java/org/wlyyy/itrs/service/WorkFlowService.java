package org.wlyyy.itrs.service;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.domain.WorkFlow;
import org.wlyyy.itrs.request.WorkFlowQuery;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 工作流基本管理服务
 */
public interface WorkFlowService {

    /**
     * 根据上传的文件进行工作流部署
     *
     * @param file 部署文件（zip类型）
     * @param deployName 部署名称
     * @return 部署结果
     */
    BaseServiceResponse<Deployment> deployWorkFlow_file(File file, String deployName);

    /**
     * 根据classpath/processes下的zip文件进行工作流部署（暂时供测试时使用）
     *
     * @param zipName zip部署文件
     * @param deployName 部署名称
     * @return 部署结果
     */
    BaseServiceResponse<Deployment> deployWorkFlow_zip(String zipName, String deployName);

    /**
     * 分页查询所有的部署信息（activiti提供分页查询）
     *
     * @param request 分页查询请求对象
     * @return 分页部署信息
     */
    BaseServicePageableResponse<Deployment> findAllDeploy(BaseServicePageableRequest<WorkFlowQuery> request);

    /**
     * 根据部署id查找流程定义的key
     *
     * @param deploymentId
     * @return
     */
    BaseServiceResponse<ProcessDefinition> findKeyByDeploymentId(String deploymentId);

    /**
     *  启动流程实例
     *
     * @return 启动流程实例结果
     */
    BaseServiceResponse<ProcessInstance> startProcess(WorkFlow workFlow);

    /**
     * 根据assignee和description分页查询任务
     *
     * @param request 分页查询请求对象
     * @param user 当前用户信息
     * @return 分页任务信息
     */
    BaseServicePageableResponse<Task> findTaskByAssignee(BaseServicePageableRequest<WorkFlowQuery> request, UserAgent user);

    /**
     * 根据assignee和description分页查询历史任务
     *
     * @param request 分页查询请求对象
     * @param user 当前用户信息
     * @return 分页任务信息
     */
    BaseServicePageableResponse<HistoricTaskInstance> findHistoricTaskByAssignee(BaseServicePageableRequest<WorkFlowQuery> request, UserAgent user);

    /**
     * 根据招聘流程id找到当前对应的Task（正需要进行处理的Task）
     *
     * @param id 招聘流程id
     * @return 当前对应的Task
     */
    BaseServiceResponse<Task> findCurrentTaskByApplyId(Long id);

    /**
     * 完成任务，同时指派下一任务的完成人
     *
     * @param workFlow
     * @return 任务完成的成功信息
     */
    BaseServiceResponse<String> completeTaskByTaskId(WorkFlow workFlow);

    /**
     * 根据招聘流程id判断该流程是否已经结束
     *
     * @param id 招聘流程id
     * @return true or false
     */
    BaseServiceResponse<Boolean> isFinishByApplyId(Long id);

    /**
     * 根据招聘流程id得到当前流程节点的处理连线
     *
     * @param id 招聘流程id
     * @return 连线集合（可转换为页面上的操作按钮）
     */
    BaseServiceResponse<List<String>> findCurrentOutcomeListByApplyId(Long id);

    /**
     *
     * @param taskId 任务id
     * @param varName 变量变量值
     * @return 变量值
     */
    BaseServiceResponse<String> findVarValueByTaskIdAndVarName(String taskId, String varName);

    /**
     *
     * @param processInstanceId 流程实例id
     * @return 该流程实例对应的招聘流程id
     */
    BaseServiceResponse<Long> findApplyIdByProcInstanceId(String processInstanceId);


    /**
     * 查询推荐任务（暂时只用于推荐人查看“员工推荐”任务，这是为了与hr和面试官的任务区分开来）
     *
     * @param query 查询对象
     * @return
     */
    Task findRecommendTask(WorkFlowQuery query);

    /**
     *  完成“员工推荐”任务
     *
     * @param workFlow
     * @return
     */
    BaseServiceResponse<String> completeRecommendTask(WorkFlow workFlow);

    /**
     * 获取部署流程图
     *
     * @param deploymentId 部署id
     * @param resourceName 资源名
     * @return InputStream
     */
    InputStream getDeployPictureInputStream(String deploymentId, String resourceName);
}

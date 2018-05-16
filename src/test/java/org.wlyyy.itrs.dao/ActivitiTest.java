package org.wlyyy.itrs.dao;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.service.WorkFlowService;
import org.wlyyy.itrs.spring.ItrsBoot;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class ActivitiTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    private WorkFlowService workFlowService;

    @Test
    public void test() {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = repositoryService
                .createDeployment()    // 创建一个部署对象
                .name("流程定义")    // 添加部署的名称
                .addZipInputStream(zipInputStream)    // 指定zip格式的文件完成部署
                .deploy();    // 完成部署
        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名称："+deployment.getName());

    }

    @Test
    public void deployWorkFlow_zip() {
        String zipName = "twoInterviews";
        String deployName = "两轮面试";
        BaseServiceResponse<Deployment> deploymentResult = workFlowService.deployWorkFlow_zip(zipName, deployName);
        if (deploymentResult.isSuccess()) {
            Deployment deployment = deploymentResult.getData();
            System.out.println(deployment.getId());
            System.out.println(deployment.getName());
            System.out.println("部署成功");
        } else {
            System.out.println("部署失败");
        }
    }

    @Test
    public void listProcessDefinition() {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .list();

        System.out.println(processDefinitionList.get(0).getName() + " " + processDefinitionList.get(0).getKey());
    }

    @Test
    public void getPicture(HttpServletResponse resoibse) {
        InputStream ins = repositoryService.getResourceAsStream("57501", "helloworld.png");

        // resoibse.getOutputStream().
    }

//    public void inputstreamtofile(InputStream ins,File file) throws IOException {
//        OutputStream os = new FileOutputStream(file);
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
//            os.write(buffer, 0, bytesRead);
//        }
//        os.close();
//        ins.close();
//    }
}

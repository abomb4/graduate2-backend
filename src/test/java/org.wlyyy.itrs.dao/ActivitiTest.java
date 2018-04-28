package org.wlyyy.itrs.dao;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.itrs.spring.ItrsBoot;

import java.io.InputStream;
import java.util.zip.ZipInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class ActivitiTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

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
}

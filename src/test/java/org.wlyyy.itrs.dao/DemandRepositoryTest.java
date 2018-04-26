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
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.request.DemandQuery;
import org.wlyyy.itrs.spring.ItrsBoot;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class DemandRepositoryTest {

    @Autowired
    private DemandRepository dao;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Test
    public void test() {
//        final Demand demand = new Demand();
//        demand.setDemandNo("11133");
//        demand.setPublisherId(2l);
//        demand.setPosition("开发");
//        demand.setHrName("吴巧丽");
//        demand.setPositionType("11");
//        demand.setTotal(15l);
//        demand.setDepartmentId(11l);
//        demand.setDegreeRequest("本科");
//        demand.setWorkingPlace("浙江省舟山市");
//
//
//        dao.insert(demand);
//
//        final Long id = demand.getId();
//        assertNotNull(id);
//
//        System.out.println(id);
//
//        // assertEquals(demand, dao.getById(id));
//
//
//        final List<Demand> byCondition = dao.findByCondition(
//                new DemandQuery()
//                        .setHrName("巧丽")
//                        .setDemandNo("11111")
//                ,
//                new PageRequest(0, 10)
//        );
//
//        System.out.println(byCondition);
//
//        final Demand demandById = dao.getById(id);
//        System.out.println(demandById);

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

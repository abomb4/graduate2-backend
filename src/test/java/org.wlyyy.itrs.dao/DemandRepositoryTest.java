package org.wlyyy.itrs.dao;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.request.DemandQuery;
import org.wlyyy.itrs.spring.ItrsBoot;

import java.util.List;

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
//        demand.setDemandNo("11152");
//        demand.setPublisherId(2l);
//        demand.setPosition("开发");
//        demand.setHrName("吴巧丽");
//        demand.setPositionType("11");
//        demand.setTotal(15l);
//        demand.setDepartmentId(11l);
//        demand.setDegreeRequest("本科");
//        demand.setWorkingPlace("浙江省舟山市");
//        demand.setProcKey("twoInterviews");
//
//
//        dao.insert(demand);
//
//        final Long id = demand.getId();
//        assertNotNull(id);
//
//        System.out.println(id);

        // assertEquals(demand, dao.getById(id));

        Order order1 = new Order(Sort.Direction.DESC, "demand_no");
        Sort sort = new Sort(order1);

        final List<Demand> byCondition = dao.findByCondition(
                new DemandQuery()
                        .setHrName("巧丽")
                ,
                new PageRequest(0, 10)
        );

        System.out.println(byCondition);

    }
}

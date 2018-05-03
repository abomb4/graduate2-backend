package org.wlyyy.itrs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.service.CachedSequenceManagementService;
import org.wlyyy.itrs.spring.ItrsBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class DemandRepositoryTest {

    @Autowired
    private DemandRepository dao;

    @Autowired
    private CachedSequenceManagementService cachedSequenceManagementService;

    @Test
    public void test() {
//        final Demand demand = new Demand();
//        demand.setDemandNo("11152");
//        demand.setPublisherId(2l);
//        demand.setJobName("开发");
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

//        Order order1 = new Order(Sort.Direction.DESC, "demand_no");
//        Sort sort = new Sort(order1);
//
//        final List<Demand> byCondition = dao.findByCondition(
//                new DemandQuery()
//                        .setHrName("巧丽")
//                ,
//                new PageRequest(0, 10)
//        );
//
//        System.out.println(byCondition);

//        Demand demand = dao.findByNo("11153");
//        System.out.println(demand);

//        Long demandNo = SequenceUtils.getSequence("seq_demand_no");
//        String demandNoStr = demandNo.toString();
//        System.out.println(demandNoStr);

        // Long demandNo = new JumpedSequenceManagerImpl(1l).getBySequenceType("seq_demand_no");
        // Long demandNo = sequenceSerivce.getBySequenceType("seq_demand_no");

        String demandNo = cachedSequenceManagementService.getBySequenceType("seq_demand_no").toString();
        System.out.println(demandNo);
    }
}

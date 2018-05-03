package org.wlyyy.itrs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.itrs.spring.ItrsBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class PositionTypeTest {

    @Autowired
    private PositionTypeRepository dao;

    @Test
    public void test() {
//        List<Long> ids = new ArrayList<>();
//        ids.add(1l);
//        ids.add(11l);
//        Long deleteCount = dao.deleteBatchIds(ids);
//        System.out.println(deleteCount);
    }
}

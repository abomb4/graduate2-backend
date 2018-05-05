package org.wlyyy.itrs.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.itrs.service.EmailEventListenerService;
import org.wlyyy.itrs.spring.ItrsBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class EmailEventListenerServiceTest {

    @Autowired
    EmailEventListenerService es;

    @Test
    public void handleOrderCreatedEvent() throws Exception {
        // es.handleOrderCreatedEvent(null);
    }
}
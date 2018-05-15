package org.wlyyy.itrs.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.event.ApplyFlowEvent;
import org.wlyyy.itrs.service.ApplyFlowService;
import org.wlyyy.itrs.spring.ItrsBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
// @Transactional
public class EmailEventListenerServiceTest {

//    @Autowired
//    EmailEventListenerService es;

    @Autowired
    RoleEventListenerService roleEventListenerService;

    @Autowired
    ApplyFlowService applyFlowService;

    @Test
    public void handleOrderCreatedEvent() throws Exception {
        // es.handleOrderCreatedEvent(null);
        ApplyFlow applyFlow = applyFlowService.findById(9l);
//        applyFlow.setUserId(4l);
//        applyFlow.setCurrentResult("二面通过");
//        applyFlow.setCandidateId(9l);
//        ApplyFlow applyFlow = new ApplyFlow();

        applyFlow.setCurrentResult("");
        applyFlow.setCurrentDealer(7l);
        String otherMessage = "指派";

        ApplyFlowEvent event  = new ApplyFlowEvent(applyFlow, otherMessage);
        roleEventListenerService.assignIntervieweeRole(event);
        // es.handleMailEvent(event);
    }
}
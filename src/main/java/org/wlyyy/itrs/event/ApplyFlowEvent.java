package org.wlyyy.itrs.event;

import org.wlyyy.itrs.domain.ApplyFlow;

import java.util.Date;

/**
 * 流程处理消息
 */
public class ApplyFlowEvent {

    private final ApplyFlow applyFlow;

    public ApplyFlowEvent(ApplyFlow applyFlow) {
        this.applyFlow = applyFlow;
    }

    public ApplyFlow getApplyFlow() {
        return applyFlow;
    }
}

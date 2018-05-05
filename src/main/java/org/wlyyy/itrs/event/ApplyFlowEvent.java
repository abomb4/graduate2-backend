package org.wlyyy.itrs.event;

import org.wlyyy.itrs.domain.ApplyFlow;

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

    @Override
    public String toString() {
        return "ApplyFlowEvent{" +
                "applyFlow=" + applyFlow +
                '}';
    }
}

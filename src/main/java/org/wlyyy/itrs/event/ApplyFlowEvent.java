package org.wlyyy.itrs.event;

import org.wlyyy.itrs.domain.ApplyFlow;

/**
 * 流程处理消息
 */
public class ApplyFlowEvent {

    private final ApplyFlow applyFlow;
    private final String otherMessage;

    public ApplyFlowEvent(ApplyFlow applyFlow, String otherMessage) {
        this.applyFlow = applyFlow;
        this.otherMessage = otherMessage;
    }

    public ApplyFlow getApplyFlow() {
        return applyFlow;
    }

    public String getOtherMessage() { return otherMessage; }

    @Override
    public String toString() {
        return "ApplyFlowEvent{" +
                "applyFlow=" + applyFlow +
                ", otherMessage='" + otherMessage + '\'' +
                '}';
    }
}

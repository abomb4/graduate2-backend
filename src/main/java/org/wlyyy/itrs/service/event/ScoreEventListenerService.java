package org.wlyyy.itrs.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.event.ApplyFlowEvent;
import org.wlyyy.itrs.service.CandidateService;
import org.wlyyy.itrs.service.ScoreService;

/**
 * 监听ApplyFlowEvent的变化，进行积分处理
 */
@Component
public class ScoreEventListenerService {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CandidateService candidateService;

    @EventListener()
    public void handleScoreEvent(ApplyFlowEvent event) throws Exception {
        ApplyFlow applyFlow = event.getApplyFlow();
        String rule = applyFlow.getCurrentResult();
        // 获取被推荐人id
        Long userId = applyFlow.getUserId();
        // 拼接用户积分变动原因
        String candidateName = candidateService.findById(applyFlow.getCandidateId()).getName();
        String memo = St.r("您推荐的【{}】{}", candidateName, rule);

        // 进行相应的积分更新
        try {
            scoreService.changeScore(rule, userId, memo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(event);
    }
}
